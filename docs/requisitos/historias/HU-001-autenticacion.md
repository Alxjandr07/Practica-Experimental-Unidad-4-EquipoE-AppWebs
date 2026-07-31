# HU-001: Inicio de sesión

- **Rol:** Administrador / Coordinador / Seguridad
- **Objetivo:** Iniciar sesión en el sistema SGROAS
- **Beneficio:** Acceder al panel de gestión según permisos

## Criterios de aceptación (Gherkin)

```gherkin
Feature: Inicio de sesion
  Scenario: Inicio de sesion exitoso
    Given un usuario registrado con email "admin@sgroas.com" y contrasena "admin123"
    When ingresa sus credenciales en el formulario de login
    Then el sistema lo redirige al dashboard
    And se crea una cookie HttpOnly con el JWT

  Scenario: Credenciales invalidas
    Given un usuario registrado con email "admin@sgroas.com"
    When ingresa una contrasena incorrecta
    Then el sistema muestra "Correo o contrasena incorrectos."
    And permanece en la pagina de login

  Scenario: Intento de acceso sin credenciales
    When el usuario intenta acceder a /dashboard sin autenticarse
    Then el sistema redirige a /login
```
