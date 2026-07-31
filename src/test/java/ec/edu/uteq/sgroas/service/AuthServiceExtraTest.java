package ec.edu.uteq.sgroas.service;

import ec.edu.uteq.sgroas.dto.AuthResponse;
import ec.edu.uteq.sgroas.dto.RefreshTokenRequest;
import ec.edu.uteq.sgroas.dto.RegisterRequest;
import ec.edu.uteq.sgroas.entity.Rol;
import ec.edu.uteq.sgroas.entity.Usuario;
import ec.edu.uteq.sgroas.repository.UsuarioRepository;
import ec.edu.uteq.sgroas.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceExtraTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void configurarRefreshExpiration() {
        ReflectionTestUtils.setField(authService, "refreshExpirationMs", 604800000L);
    }

    private Usuario usuarioEjemplo() {
        return Usuario.builder()
                .id(1L)
                .nombre("Administrador SGROAS")
                .email("admin@sgroas.com")
                .passwordHash("password-encriptado")
                .rol(Rol.ROLE_ADMIN)
                .activo(true)
                .creadoEn(Instant.now())
                .actualizadoEn(Instant.now())
                .build();
    }

    private void simularGeneracionTokens(Usuario usuario) {
        when(jwtService.generarToken(usuario)).thenReturn("access-token-prueba");
        when(tokenService.generarRefreshToken(eq("admin@sgroas.com"), eq(604800000L)))
                .thenReturn("refresh-token-prueba");
        when(jwtService.getExpirationMs()).thenReturn(3600000L);
    }

    @Test
    void registrarDebeRetornarTokens() {
        Usuario usuario = usuarioEjemplo();
        when(usuarioRepository.existsByEmail("admin@sgroas.com")).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("password-encriptado");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        simularGeneracionTokens(usuario);

        AuthResponse response = authService.registrar(
                new RegisterRequest("Administrador SGROAS", "admin@sgroas.com", "123456", "ROLE_ADMIN")
        );

        assertNotNull(response);
        assertEquals("access-token-prueba", response.accessToken());
        assertEquals("ROLE_ADMIN", response.rol());
    }

    @Test
    void registrarConEmailExistenteDebeLanzarExcepcion() {
        when(usuarioRepository.existsByEmail("admin@sgroas.com")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> authService.registrar(
                        new RegisterRequest("Administrador", "admin@sgroas.com", "123456", "ROLE_ADMIN")
                ));
    }

    @Test
    void registrarConRolInvalidoDebeLanzarExcepcion() {
        when(usuarioRepository.existsByEmail("admin@sgroas.com")).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> authService.registrar(
                        new RegisterRequest("Administrador", "admin@sgroas.com", "123456", "ROL_INVALIDO")
                ));
    }

    @Test
    void refreshDebeRotarToken() {
        Usuario usuario = usuarioEjemplo();
        when(tokenService.obtenerEmailDesdeRefreshToken("refresh-token-prueba"))
                .thenReturn("admin@sgroas.com");
        when(usuarioRepository.findByEmail("admin@sgroas.com"))
                .thenReturn(Optional.of(usuario));
        simularGeneracionTokens(usuario);

        AuthResponse response = authService.refresh(
                new RefreshTokenRequest("refresh-token-prueba")
        );

        assertNotNull(response);
        assertEquals("access-token-prueba", response.accessToken());
        verify(tokenService).eliminarRefreshToken("refresh-token-prueba");
    }

    @Test
    void refreshConUsuarioInactivoDebeLanzarExcepcion() {
        Usuario inactivo = usuarioEjemplo();
        inactivo.setActivo(false);
        when(tokenService.obtenerEmailDesdeRefreshToken("refresh-token-prueba"))
                .thenReturn("admin@sgroas.com");
        when(usuarioRepository.findByEmail("admin@sgroas.com"))
                .thenReturn(Optional.of(inactivo));

        assertThrows(BadCredentialsException.class,
                () -> authService.refresh(new RefreshTokenRequest("refresh-token-prueba")));
    }

    @Test
    void refreshConEmailInexistenteDebeLanzarExcepcion() {
        when(tokenService.obtenerEmailDesdeRefreshToken("refresh-token-prueba"))
                .thenReturn("desconocido@sgroas.com");
        when(usuarioRepository.findByEmail("desconocido@sgroas.com"))
                .thenReturn(Optional.empty());

        assertThrows(BadCredentialsException.class,
                () -> authService.refresh(new RefreshTokenRequest("refresh-token-prueba")));
    }

    @Test
    void logoutDebeInvalidarTokens() {
        authService.logout("access-token-prueba",
                new RefreshTokenRequest("refresh-token-prueba"));

        verify(tokenService).agregarAccessTokenABlacklist("access-token-prueba");
        verify(tokenService).eliminarRefreshToken("refresh-token-prueba");
    }
}
