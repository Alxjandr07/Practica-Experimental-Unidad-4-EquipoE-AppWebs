# HU-003: Gestión de usuarios del sistema

- **Rol:** Administrador
- **Objetivo:** Administrar las cuentas de usuario del sistema
- **Beneficio:** Controlar el acceso y los permisos del personal

## Criterios de aceptación (Gherkin)

```gherkin
Feature: Gestion de usuarios
  Scenario: Listar usuarios
    Given el usuario autenticado como administrador
    When navega a la seccion de usuarios
    Then ve una tabla con todos los usuarios del sistema
    And cada fila muestra nombre, email, rol y estado

  Scenario: Crear usuario
    Given el usuario autenticado como administrador
    When completa el formulario con datos de un nuevo usuario
    Then el sistema crea la cuenta
    And el nuevo usuario puede iniciar sesion

  Scenario: Editar usuario
    Given existe un usuario registrado
    When el administrador modifica su rol o datos
    Then el sistema actualiza el registro

  Scenario: Desactivar usuario
    Given existe un usuario activo
    When el administrador lo desactiva
    Then el usuario ya no puede iniciar sesion
```
