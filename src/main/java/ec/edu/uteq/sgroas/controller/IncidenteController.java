package ec.edu.uteq.sgroas.controller;

import ec.edu.uteq.sgroas.dto.IncidenteRequest;
import ec.edu.uteq.sgroas.dto.IncidenteResponse;
import ec.edu.uteq.sgroas.service.IncidenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/incidentes")
@RequiredArgsConstructor
public class IncidenteController {

    private final IncidenteService incidenteService;

    @GetMapping
    public ResponseEntity<Page<IncidenteResponse>> listar(
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(incidenteService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidenteResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(incidenteService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<IncidenteResponse> crear(
            @Valid @RequestBody IncidenteRequest request
    ) {
        IncidenteResponse response = incidenteService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncidenteResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody IncidenteRequest request
    ) {
        return ResponseEntity.ok(incidenteService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        incidenteService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
