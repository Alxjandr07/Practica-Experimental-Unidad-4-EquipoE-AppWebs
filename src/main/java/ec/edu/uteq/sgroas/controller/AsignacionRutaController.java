package ec.edu.uteq.sgroas.controller;

import ec.edu.uteq.sgroas.dto.AsignacionRutaRequest;
import ec.edu.uteq.sgroas.dto.AsignacionRutaResponse;
import ec.edu.uteq.sgroas.service.AsignacionRutaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/asignaciones")
@RequiredArgsConstructor
public class AsignacionRutaController {

    private final AsignacionRutaService asignacionRutaService;

    @GetMapping
    public ResponseEntity<Page<AsignacionRutaResponse>> listar(
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(asignacionRutaService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsignacionRutaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(asignacionRutaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AsignacionRutaResponse> crear(
            @Valid @RequestBody AsignacionRutaRequest request
    ) {
        AsignacionRutaResponse response = asignacionRutaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsignacionRutaResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody AsignacionRutaRequest request
    ) {
        return ResponseEntity.ok(asignacionRutaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        asignacionRutaService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
