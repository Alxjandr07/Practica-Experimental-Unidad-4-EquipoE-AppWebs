# CU-001: Inicio de sesión

| Campo | Valor |
|---|---|
| **ID** | CU-001 |
| **Nombre** | Inicio de sesion |
| **Actor principal** | Usuario del sistema (Admin / Coordinador / Seguridad) |
| **Nivel** | 1 — Objetivo del usuario |
| **Precondiciones** | El usuario posee credenciales validas |
| **Postcondiciones** | El usuario obtiene un JWT en cookie HttpOnly |

## Escenario principal de éxito

1. El usuario ingresa su email y contrasena en el formulario de login.
2. El sistema valida las credenciales contra la base de datos.
3. El sistema genera un JWT con 7 claims (iss, sub, aud, exp, nbf, iat, jti).
4. El sistema establece una cookie HttpOnly + Secure + SameSite=Strict con el JWT.
5. El sistema redirige al dashboard.

## Extensiones

| Paso | Condición | Manejo |
|---|---|---|
| 2a | Email no registrado | Sistema muestra "Credenciales invalidas" |
| 2b | Cuenta desactivada | Sistema muestra "Credenciales invalidas" |
| 2c | Contrasena incorrecta | Sistema muestra "Credenciales invalidas" |
| 2d | 6 intentos fallidos consecutivos | Sistema bloquea temporalmente con 429 Too Many Requests |
