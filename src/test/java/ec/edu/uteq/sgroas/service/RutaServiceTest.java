package ec.edu.uteq.sgroas.service;

import ec.edu.uteq.sgroas.dto.RutaRequest;
import ec.edu.uteq.sgroas.dto.RutaResponse;
import ec.edu.uteq.sgroas.entity.EstadoRuta;
import ec.edu.uteq.sgroas.entity.Ruta;
import ec.edu.uteq.sgroas.repository.RutaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RutaServiceTest {

    @Mock
    private RutaRepository rutaRepository;

    @InjectMocks
    private RutaService rutaService;

    private Ruta rutaEjemplo() {
        return Ruta.builder()
                .id(1L)
                .codigo("R-001")
                .nombre("Quito - Guayaquil")
                .origen("Quito")
                .destino("Guayaquil")
                .distanciaKm(420.0)
                .duracionEstimadaMin(480)
                .estado(EstadoRuta.ACTIVA)
                .activo(true)
                .creadoEn(Instant.now())
                .actualizadoEn(Instant.now())
                .build();
    }

    private RutaRequest requestEjemplo() {
        return new RutaRequest(
                "R-001", "Quito - Guayaquil", "Quito", "Guayaquil",
                420.0, 480, "ACTIVA"
        );
    }

    @Test
    void listarDebeRetornarPagina() {
        PageRequest pageable = PageRequest.of(0, 10);
        when(rutaRepository.findByActivoTrue(pageable))
                .thenReturn(new PageImpl<>(List.of(rutaEjemplo())));

        Page<RutaResponse> pagina = rutaService.listar(pageable);

        assertEquals(1, pagina.getTotalElements());
        assertEquals("R-001", pagina.getContent().get(0).codigo());
    }

    @Test
    void buscarPorIdDebeRetornarRuta() {
        when(rutaRepository.findById(1L)).thenReturn(Optional.of(rutaEjemplo()));

        RutaResponse response = rutaService.buscarPorId(1L);

        assertEquals(1L, response.id());
        assertEquals("Quito", response.origen());
    }

    @Test
    void buscarPorIdInexistenteDebeLanzarExcepcion() {
        when(rutaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> rutaService.buscarPorId(99L));
    }

    @Test
    void crearDebeGuardarYRetornar() {
        when(rutaRepository.existsByCodigo("R-001")).thenReturn(false);
        when(rutaRepository.save(any(Ruta.class))).thenReturn(rutaEjemplo());

        RutaResponse response = rutaService.crear(requestEjemplo());

        assertEquals("R-001", response.codigo());
        verify(rutaRepository).save(any(Ruta.class));
    }

    @Test
    void crearConCodigoDuplicadoDebeLanzarExcepcion() {
        when(rutaRepository.existsByCodigo("R-001")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> rutaService.crear(requestEjemplo()));
    }

    @Test
    void crearConEstadoInvalidoDebeLanzarExcepcion() {
        when(rutaRepository.existsByCodigo("R-001")).thenReturn(false);
        RutaRequest request = new RutaRequest(
                "R-001", "Quito - Guayaquil", "Quito", "Guayaquil",
                420.0, 480, "INVALIDO"
        );

        assertThrows(IllegalArgumentException.class,
                () -> rutaService.crear(request));
    }

    @Test
    void actualizarDebeModificarYRetornar() {
        when(rutaRepository.findById(1L)).thenReturn(Optional.of(rutaEjemplo()));
        when(rutaRepository.save(any(Ruta.class))).thenReturn(rutaEjemplo());

        RutaResponse response = rutaService.actualizar(1L, requestEjemplo());

        assertEquals(1L, response.id());
    }

    @Test
    void actualizarConCodigoDuplicadoDebeLanzarExcepcion() {
        when(rutaRepository.findById(1L)).thenReturn(Optional.of(rutaEjemplo()));
        when(rutaRepository.existsByCodigo("R-999")).thenReturn(true);

        RutaRequest request = new RutaRequest(
                "R-999", "Quito - Guayaquil", "Quito", "Guayaquil",
                420.0, 480, "ACTIVA"
        );

        assertThrows(IllegalArgumentException.class,
                () -> rutaService.actualizar(1L, request));
    }

    @Test
    void desactivarDebeCambiarEstado() {
        when(rutaRepository.findById(1L)).thenReturn(Optional.of(rutaEjemplo()));

        rutaService.desactivar(1L);

        verify(rutaRepository).save(argThat(r ->
                !r.getActivo() && r.getEstado() == EstadoRuta.INACTIVA));
    }
}
