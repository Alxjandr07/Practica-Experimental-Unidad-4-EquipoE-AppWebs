package ec.edu.uteq.sgroas.service;

import ec.edu.uteq.sgroas.dto.IncidenteRequest;
import ec.edu.uteq.sgroas.dto.IncidenteResponse;
import ec.edu.uteq.sgroas.entity.*;
import ec.edu.uteq.sgroas.repository.AsignacionRutaRepository;
import ec.edu.uteq.sgroas.repository.IncidenteRepository;
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
public class IncidenteService {

    private final IncidenteRepository incidenteRepository;
    private final AsignacionRutaRepository asignacionRutaRepository;

    public Page<IncidenteResponse> listar(Pageable pageable) {
        List<IncidenteResponse> contenido = listarCacheable(pageable);
        return new PageImpl<>(contenido, pageable, contenido.size());
    }

    @Cacheable(value = "incidentes", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public List<IncidenteResponse> listarCacheable(Pageable pageable) {
        return incidenteRepository.findByActivoTrue(pageable)
                .map(this::mapearAResponse)
                .getContent();
    }

    public IncidenteResponse buscarPorId(Long id) {
        Incidente incidente = obtenerIncidenteActivo(id);
        return mapearAResponse(incidente);
    }

    @CacheEvict(value = "incidentes", allEntries = true)
    public IncidenteResponse crear(IncidenteRequest request) {
        AsignacionRuta asignacion = asignacionRutaRepository.findById(request.asignacionId())
                .filter(AsignacionRuta::getActivo)
                .orElseThrow(() -> new IllegalArgumentException("Asignacion no encontrada"));

        Incidente incidente = Incidente.builder()
                .asignacion(asignacion)
                .reportadoPor(request.reportadoPor())
                .tipo(convertirTipo(request.tipo()))
                .descripcion(request.descripcion())
                .fechaIncidente(request.fechaIncidente())
                .ubicacion(request.ubicacion())
                .gravedad(convertirGravedad(request.gravedad()))
                .estado(convertirEstado(request.estado()))
                .activo(true)
                .creadoEn(Instant.now())
                .actualizadoEn(Instant.now())
                .build();

        Incidente incidenteGuardado = incidenteRepository.save(incidente);
        return mapearAResponse(incidenteGuardado);
    }

    @CacheEvict(value = "incidentes", allEntries = true)
    public IncidenteResponse actualizar(Long id, IncidenteRequest request) {
        Incidente incidente = obtenerIncidenteActivo(id);

        AsignacionRuta asignacion = asignacionRutaRepository.findById(request.asignacionId())
                .filter(AsignacionRuta::getActivo)
                .orElseThrow(() -> new IllegalArgumentException("Asignacion no encontrada"));

        incidente.setAsignacion(asignacion);
        incidente.setReportadoPor(request.reportadoPor());
        incidente.setTipo(convertirTipo(request.tipo()));
        incidente.setDescripcion(request.descripcion());
        incidente.setFechaIncidente(request.fechaIncidente());
        incidente.setUbicacion(request.ubicacion());
        incidente.setGravedad(convertirGravedad(request.gravedad()));
        incidente.setEstado(convertirEstado(request.estado()));
        incidente.setActualizadoEn(Instant.now());

        Incidente incidenteActualizado = incidenteRepository.save(incidente);
        return mapearAResponse(incidenteActualizado);
    }

    @CacheEvict(value = "incidentes", allEntries = true)
    public void desactivar(Long id) {
        Incidente incidente = obtenerIncidenteActivo(id);
        incidente.setActivo(false);
        incidente.setEstado(EstadoIncidente.CERRADO);
        incidente.setActualizadoEn(Instant.now());
        incidenteRepository.save(incidente);
    }

    private Incidente obtenerIncidenteActivo(Long id) {
        return incidenteRepository.findById(id)
                .filter(Incidente::getActivo)
                .orElseThrow(() -> new IllegalArgumentException("Incidente no encontrado"));
    }

    private TipoIncidente convertirTipo(String tipo) {
        try {
            return TipoIncidente.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de incidente no valido");
        }
    }

    private GravedadIncidente convertirGravedad(String gravedad) {
        try {
            return GravedadIncidente.valueOf(gravedad.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Gravedad de incidente no valida");
        }
    }

    private EstadoIncidente convertirEstado(String estado) {
        try {
            return EstadoIncidente.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado de incidente no valido");
        }
    }

    private IncidenteResponse mapearAResponse(Incidente incidente) {
        return new IncidenteResponse(
                incidente.getId(),
                incidente.getAsignacion().getId(),
                incidente.getReportadoPor(),
                incidente.getTipo().name(),
                incidente.getDescripcion(),
                incidente.getFechaIncidente(),
                incidente.getUbicacion(),
                incidente.getGravedad().name(),
                incidente.getEstado().name(),
                incidente.getActivo(),
                incidente.getCreadoEn(),
                incidente.getActualizadoEn()
        );
    }
}
