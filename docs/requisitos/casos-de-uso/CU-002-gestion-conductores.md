# CU-002: Gestión de conductores

| Campo | Valor |
|---|---|
| **ID** | CU-002 |
| **Nombre** | Gestion de conductores |
| **Actor principal** | Administrador / Coordinador |
| **Nivel** | 2 — Tarea del usuario |
| **Precondiciones** | Usuario autenticado con rol ADMIN o COORDINADOR |
| **Postcondiciones** | Conductor creado, actualizado o desactivado segun corresponda |

## Escenario principal de éxito — Crear conductor

1. El usuario navega a la seccion Flota Vehicular.
2. El sistema muestra el listado paginado de conductores.
3. El usuario hace clic en "+ Nuevo conductor".
4. El sistema muestra el formulario de registro.
5. El usuario completa los datos: nombres, apellidos, cedula, licencia, telefono, email.
6. El usuario hace clic en "Crear conductor".
7. El sistema valida los datos.
8. El sistema guarda el conductor en la base de datos.
9. El sistema redirige al listado con el nuevo conductor visible.

## Extensiones

| Paso | Condición | Manejo |
|---|---|---|
| 7a | Cedula duplicada | Sistema muestra error 422 con ProblemDetails |
| 7b | Licencia duplicada | Sistema muestra error 422 |
| 7c | Campos obligatorios vacios | Sistema marca los campos en rojo |
