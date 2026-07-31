# CU-003: Gestión de usuarios del sistema

| Campo | Valor |
|---|---|
| **ID** | CU-003 |
| **Nombre** | Gestion de usuarios del sistema |
| **Actor principal** | Administrador |
| **Nivel** | 2 — Tarea del usuario |
| **Precondiciones** | Usuario autenticado con rol ADMIN |
| **Postcondiciones** | Usuario creado, actualizado o desactivado segun corresponda |

## Escenario principal de éxito — Crear usuario

1. El administrador navega a Usuarios y Roles.
2. El sistema muestra el listado paginado de usuarios.
3. El administrador hace clic en "+ Nuevo usuario".
4. El sistema muestra el formulario de registro.
5. El administrador ingresa nombre, email, contrasena y selecciona un rol.
6. El administrador hace clic en "Crear usuario".
7. El sistema valida que el email no exista.
8. El sistema crea la cuenta con password hasheado (BCrypt).
9. El sistema redirige al listado.

## Extensiones

| Paso | Condición | Manejo |
|---|---|---|
| 7a | Email ya registrado | Sistema muestra error: "Ya existe un usuario con ese email" |
| 7b | Rol invalido | Sistema muestra error de validacion |
| 8a | Error de base de datos | Sistema responde con 500 y ProblemDetails |
