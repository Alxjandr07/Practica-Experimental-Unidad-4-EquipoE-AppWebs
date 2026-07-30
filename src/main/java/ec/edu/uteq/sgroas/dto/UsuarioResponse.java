package ec.edu.uteq.sgroas.dto;

import java.time.Instant;

public record UsuarioResponse(
        Long id,
        String nombre,
        String email,
        String rol,
        Boolean activo,
        Instant creadoEn,
        Instant actualizadoEn
) {
}
