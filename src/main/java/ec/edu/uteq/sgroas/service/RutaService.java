package ec.edu.uteq.sgroas.service;

import ec.edu.uteq.sgroas.dto.RutaRequest;
import ec.edu.uteq.sgroas.dto.RutaResponse;
import ec.edu.uteq.sgroas.entity.EstadoRuta;
import ec.edu.uteq.sgroas.entity.Ruta;
import ec.edu.uteq.sgroas.repository.RutaRepository;
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
public class RutaService {

    private final RutaRepository rutaRepository;

    public Page<RutaResponse> listar(Pageable pageable) {
        List<RutaResponse> contenido = listarCacheable(pageable);
        return new PageImpl<>(contenido, pageable, contenido.size());
    }

    @Cacheable(value = "rutas", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public List<RutaResponse> listarCacheable(Pageable pageable) {
        return rutaRepository.findByActivoTrue(pageable)
                .map(this::mapearAResponse)
                .getContent();
    }

    public RutaResponse buscarPorId(Long id) {
        Ruta ruta = obtenerRutaActiva(id);
        return mapearAResponse(ruta);
    }

    @CacheEvict(value = "rutas", allEntries = true)
    public RutaResponse crear(RutaRequest request) {
        if (rutaRepository.existsByCodigo(request.codigo())) {
            throw new IllegalArgumentException("Ya existe una ruta con ese codigo");
        }

        Ruta ruta = Ruta.builder()
                .codigo(request.codigo())
                .nombre(request.nombre())
                .origen(request.origen())
                .destino(request.destino())
                .distanciaKm(request.distanciaKm())
                .duracionEstimadaMin(request.duracionEstimadaMin())
                .estado(convertirEstado(request.estado()))
                .activo(true)
                .creadoEn(Instant.now())
                .actualizadoEn(Instant.now())
                .build();

        Ruta rutaGuardada = rutaRepository.save(ruta);
        return mapearAResponse(rutaGuardada);
    }

    @CacheEvict(value = "rutas", allEntries = true)
    public RutaResponse actualizar(Long id, RutaRequest request) {
        Ruta ruta = obtenerRutaActiva(id);

        if (!ruta.getCodigo().equals(request.codigo())
                && rutaRepository.existsByCodigo(request.codigo())) {
            throw new IllegalArgumentException("Ya existe una ruta con ese codigo");
        }

        ruta.setCodigo(request.codigo());
        ruta.setNombre(request.nombre());
        ruta.setOrigen(request.origen());
        ruta.setDestino(request.destino());
        ruta.setDistanciaKm(request.distanciaKm());
        ruta.setDuracionEstimadaMin(request.duracionEstimadaMin());
        ruta.setEstado(convertirEstado(request.estado()));
        ruta.setActualizadoEn(Instant.now());

        Ruta rutaActualizada = rutaRepository.save(ruta);
        return mapearAResponse(rutaActualizada);
    }

    @CacheEvict(value = "rutas", allEntries = true)
    public void desactivar(Long id) {
        Ruta ruta = obtenerRutaActiva(id);
        ruta.setActivo(false);
        ruta.setEstado(EstadoRuta.INACTIVA);
        ruta.setActualizadoEn(Instant.now());
        rutaRepository.save(ruta);
    }

    private Ruta obtenerRutaActiva(Long id) {
        return rutaRepository.findById(id)
                .filter(Ruta::getActivo)
                .orElseThrow(() -> new IllegalArgumentException("Ruta no encontrada"));
    }

    private EstadoRuta convertirEstado(String estado) {
        try {
            return EstadoRuta.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado de ruta no valido");
        }
    }

    private RutaResponse mapearAResponse(Ruta ruta) {
        return new RutaResponse(
                ruta.getId(),
                ruta.getCodigo(),
                ruta.getNombre(),
                ruta.getOrigen(),
                ruta.getDestino(),
                ruta.getDistanciaKm(),
                ruta.getDuracionEstimadaMin(),
                ruta.getEstado().name(),
                ruta.getActivo(),
                ruta.getCreadoEn(),
                ruta.getActualizadoEn()
        );
    }
}
