package ec.edu.uteq.sgroas.service;

import ec.edu.uteq.sgroas.dto.VehiculoRequest;
import ec.edu.uteq.sgroas.dto.VehiculoResponse;
import ec.edu.uteq.sgroas.entity.EstadoVehiculo;
import ec.edu.uteq.sgroas.entity.Vehiculo;
import ec.edu.uteq.sgroas.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public Page<VehiculoResponse> listar(Pageable pageable) {
        List<VehiculoResponse> contenido = listarCacheable(pageable);
        return new PageImpl<>(contenido, pageable, contenido.size());
    }

    @Cacheable(value = "vehiculos", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public List<VehiculoResponse> listarCacheable(Pageable pageable) {
        return vehiculoRepository.findByActivoTrue(pageable)
                .map(this::mapearAResponse)
                .getContent();
    }

    public VehiculoResponse buscarPorId(Long id) {
        Vehiculo vehiculo = obtenerVehiculoActivo(id);
        return mapearAResponse(vehiculo);
    }

    @CacheEvict(value = "vehiculos", allEntries = true)
    public VehiculoResponse crear(VehiculoRequest request) {
        if (vehiculoRepository.existsByPlaca(request.placa())) {
            throw new IllegalArgumentException("Ya existe un vehiculo con esa placa");
        }

        Vehiculo vehiculo = Vehiculo.builder()
                .placa(request.placa())
                .marca(request.marca())
                .modelo(request.modelo())
                .anio(request.anio())
                .capacidadPasajeros(request.capacidadPasajeros())
                .numeroMotor(request.numeroMotor())
                .numeroChasis(request.numeroChasis())
                .color(request.color())
                .estado(convertirEstado(request.estado()))
                .activo(true)
                .creadoEn(Instant.now())
                .actualizadoEn(Instant.now())
                .build();

        Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo);
        return mapearAResponse(vehiculoGuardado);
    }

    @CacheEvict(value = "vehiculos", allEntries = true)
    public VehiculoResponse actualizar(Long id, VehiculoRequest request) {
        Vehiculo vehiculo = obtenerVehiculoActivo(id);

        if (!vehiculo.getPlaca().equals(request.placa())
                && vehiculoRepository.existsByPlaca(request.placa())) {
            throw new IllegalArgumentException("Ya existe un vehiculo con esa placa");
        }

        vehiculo.setPlaca(request.placa());
        vehiculo.setMarca(request.marca());
        vehiculo.setModelo(request.modelo());
        vehiculo.setAnio(request.anio());
        vehiculo.setCapacidadPasajeros(request.capacidadPasajeros());
        vehiculo.setNumeroMotor(request.numeroMotor());
        vehiculo.setNumeroChasis(request.numeroChasis());
        vehiculo.setColor(request.color());
        vehiculo.setEstado(convertirEstado(request.estado()));
        vehiculo.setActualizadoEn(Instant.now());

        Vehiculo vehiculoActualizado = vehiculoRepository.save(vehiculo);
        return mapearAResponse(vehiculoActualizado);
    }

    @CacheEvict(value = "vehiculos", allEntries = true)
    public void desactivar(Long id) {
        Vehiculo vehiculo = obtenerVehiculoActivo(id);
        vehiculo.setActivo(false);
        vehiculo.setEstado(EstadoVehiculo.FUERA_DE_SERVICIO);
        vehiculo.setActualizadoEn(Instant.now());
        vehiculoRepository.save(vehiculo);
    }

    private Vehiculo obtenerVehiculoActivo(Long id) {
        return vehiculoRepository.findById(id)
                .filter(Vehiculo::getActivo)
                .orElseThrow(() -> new IllegalArgumentException("Vehiculo no encontrado"));
    }

    private EstadoVehiculo convertirEstado(String estado) {
        try {
            return EstadoVehiculo.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado de vehiculo no valido");
        }
    }

    private VehiculoResponse mapearAResponse(Vehiculo vehiculo) {
        return new VehiculoResponse(
                vehiculo.getId(),
                vehiculo.getPlaca(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getAnio(),
                vehiculo.getCapacidadPasajeros(),
                vehiculo.getNumeroMotor(),
                vehiculo.getNumeroChasis(),
                vehiculo.getColor(),
                vehiculo.getEstado().name(),
                vehiculo.getActivo(),
                vehiculo.getCreadoEn(),
                vehiculo.getActualizadoEn()
        );
    }
}
