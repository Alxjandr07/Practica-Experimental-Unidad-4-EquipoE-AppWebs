package ec.edu.uteq.sgroas.controller;

import ec.edu.uteq.sgroas.dto.RutaRequest;
import ec.edu.uteq.sgroas.dto.RutaResponse;
import ec.edu.uteq.sgroas.service.RutaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rutas")
@RequiredArgsConstructor
public class RutaController {

    private final RutaService rutaService;

    @GetMapping
    public ResponseEntity<Page<RutaResponse>> listar(
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(rutaService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RutaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(rutaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<RutaResponse> crear(
            @Valid @RequestBody RutaRequest request
    ) {
        RutaResponse response = rutaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RutaResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RutaRequest request
    ) {
        return ResponseEntity.ok(rutaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        rutaService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
