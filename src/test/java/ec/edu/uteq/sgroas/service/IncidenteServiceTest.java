package ec.edu.uteq.sgroas.service;

import ec.edu.uteq.sgroas.dto.IncidenteRequest;
import ec.edu.uteq.sgroas.dto.IncidenteResponse;
import ec.edu.uteq.sgroas.entity.*;
import ec.edu.uteq.sgroas.repository.AsignacionRutaRepository;
import ec.edu.uteq.sgroas.repository.IncidenteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncidenteServiceTest {

    @Mock
    private IncidenteRepository incidenteRepository;

    @Mock
    private AsignacionRutaRepository asignacionRutaRepository;

    @InjectMocks
    private IncidenteService incidenteService;

    private AsignacionRuta asignacionEjemplo() {
        Conductor conductor = Conductor.builder()
                .id(1L).nombres("Carlos").apellidos("Mendoza")
                .cedula("1200000001").numeroLicencia("LIC-001")
                .tipoLicencia("E").fechaVencimientoLicencia(LocalDate.now().plusDays(30))
                .telefono("0988888888").email("carlos@sgroas.com")
                .estado(EstadoConductor.ACTIVO).activo(true)
                .creadoEn(Instant.now()).actualizadoEn(Instant.now())
                .build();
        Vehiculo vehiculo = Vehiculo.builder()
                .id(1L).placa("GTU-001").marca("Toyota").modelo("Hiace")
                .anio(2020).capacidadPasajeros(14).numeroMotor("MOT")
                .numeroChasis("CHAS").color("Blanco")
                .estado(EstadoVehiculo.ACTIVO).activo(true)
                .creadoEn(Instant.now()).actualizadoEn(Instant.now())
                .build();
        Ruta ruta = Ruta.builder()
                .id(1L).codigo("R-001").nombre("Quito-Guayaquil")
                .origen("Quito").destino("Guayaquil").distanciaKm(420.0)
                .duracionEstimadaMin(480).estado(EstadoRuta.ACTIVA).activo(true)
                .creadoEn(Instant.now()).actualizadoEn(Instant.now())
                .build();
        return AsignacionRuta.builder()
                .id(1L).conductor(conductor).vehiculo(vehiculo).ruta(ruta)
                .fechaAsignacion(LocalDate.now()).fechaInicio(LocalDate.now())
                .fechaFin(LocalDate.now().plusDays(1)).estado(EstadoAsignacion.ACTIVA)
                .activo(true).creadoEn(Instant.now()).actualizadoEn(Instant.now())
                .build();
    }

    private Incidente incidenteEjemplo() {
        return Incidente.builder()
                .id(1L)
                .asignacion(asignacionEjemplo())
                .reportadoPor("Carlos Mendoza")
                .tipo(TipoIncidente.AVERIA_MECANICA)
                .descripcion("Falla en el motor")
                .fechaIncidente(LocalDateTime.now())
                .ubicacion("Km 12 Via Quito")
                .gravedad(GravedadIncidente.MEDIA)
                .estado(EstadoIncidente.REPORTADO)
                .activo(true)
                .creadoEn(Instant.now())
                .actualizadoEn(Instant.now())
                .build();
    }

    private IncidenteRequest requestEjemplo() {
        return new IncidenteRequest(
                1L, "Carlos Mendoza", "AVERIA_MECANICA", "Falla en el motor",
                LocalDateTime.now(), "Km 12 Via Quito", "MEDIA", "REPORTADO"
        );
    }

    @Test
    void listarDebeRetornarPagina() {
        PageRequest pageable = PageRequest.of(0, 10);
        when(incidenteRepository.findByActivoTrue(pageable))
                .thenReturn(new PageImpl<>(List.of(incidenteEjemplo())));

        Page<IncidenteResponse> pagina = incidenteService.listar(pageable);

        assertEquals(1, pagina.getTotalElements());
        assertEquals("AVERIA_MECANICA", pagina.getContent().get(0).tipo());
    }

    @Test
    void buscarPorIdDebeRetornarIncidente() {
        when(incidenteRepository.findById(1L)).thenReturn(Optional.of(incidenteEjemplo()));

        IncidenteResponse response = incidenteService.buscarPorId(1L);

        assertEquals(1L, response.id());
        assertEquals(1L, response.asignacionId());
    }

    @Test
    void buscarPorIdInexistenteDebeLanzarExcepcion() {
        when(incidenteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> incidenteService.buscarPorId(99L));
    }

    @Test
    void crearDebeGuardarYRetornar() {
        when(asignacionRutaRepository.findById(1L))
                .thenReturn(Optional.of(asignacionEjemplo()));
        when(incidenteRepository.save(any(Incidente.class)))
                .thenReturn(incidenteEjemplo());

        IncidenteResponse response = incidenteService.crear(requestEjemplo());

        assertNotNull(response);
        assertEquals("MEDIA", response.gravedad());
        verify(incidenteRepository).save(any(Incidente.class));
    }

    @Test
    void crearConAsignacionInexistenteDebeLanzarExcepcion() {
        when(asignacionRutaRepository.findById(99L)).thenReturn(Optional.empty());

        IncidenteRequest request = new IncidenteRequest(
                99L, "Carlos Mendoza", "AVERIA_MECANICA", "Falla",
                LocalDateTime.now(), "Km 12", "MEDIA", "REPORTADO"
        );

        assertThrows(IllegalArgumentException.class,
                () -> incidenteService.crear(request));
    }

    @Test
    void crearConTipoInvalidoDebeLanzarExcepcion() {
        when(asignacionRutaRepository.findById(1L))
                .thenReturn(Optional.of(asignacionEjemplo()));

        IncidenteRequest request = new IncidenteRequest(
                1L, "Carlos Mendoza", "TIPO_INVALIDO", "Falla",
                LocalDateTime.now(), "Km 12", "MEDIA", "REPORTADO"
        );

        assertThrows(IllegalArgumentException.class,
                () -> incidenteService.crear(request));
    }

    @Test
    void crearConGravedadInvalidaDebeLanzarExcepcion() {
        when(asignacionRutaRepository.findById(1L))
                .thenReturn(Optional.of(asignacionEjemplo()));

        IncidenteRequest request = new IncidenteRequest(
                1L, "Carlos Mendoza", "AVERIA_MECANICA", "Falla",
                LocalDateTime.now(), "Km 12", "INVALIDA", "REPORTADO"
        );

        assertThrows(IllegalArgumentException.class,
                () -> incidenteService.crear(request));
    }

    @Test
    void crearConEstadoInvalidoDebeLanzarExcepcion() {
        when(asignacionRutaRepository.findById(1L))
                .thenReturn(Optional.of(asignacionEjemplo()));

        IncidenteRequest request = new IncidenteRequest(
                1L, "Carlos Mendoza", "AVERIA_MECANICA", "Falla",
                LocalDateTime.now(), "Km 12", "MEDIA", "INVALIDO"
        );

        assertThrows(IllegalArgumentException.class,
                () -> incidenteService.crear(request));
    }

    @Test
    void actualizarDebeModificarYRetornar() {
        when(incidenteRepository.findById(1L)).thenReturn(Optional.of(incidenteEjemplo()));
        when(asignacionRutaRepository.findById(1L))
                .thenReturn(Optional.of(asignacionEjemplo()));
        when(incidenteRepository.save(any(Incidente.class)))
                .thenReturn(incidenteEjemplo());

        IncidenteResponse response = incidenteService.actualizar(1L, requestEjemplo());

        assertEquals(1L, response.id());
        verify(incidenteRepository).save(any(Incidente.class));
    }

    @Test
    void desactivarDebeCambiarEstado() {
        when(incidenteRepository.findById(1L)).thenReturn(Optional.of(incidenteEjemplo()));

        incidenteService.desactivar(1L);

        verify(incidenteRepository).save(argThat(i ->
                !i.getActivo() && i.getEstado() == EstadoIncidente.CERRADO));
    }
}
