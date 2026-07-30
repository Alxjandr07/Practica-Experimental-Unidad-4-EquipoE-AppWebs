# HU-002: Gestión de conductores

- **Rol:** Administrador / Coordinador
- **Objetivo:** Administrar el registro de conductores
- **Beneficio:** Mantener actualizada la base de datos de conductores de la cooperativa

## Criterios de aceptación (Gherkin)

```gherkin
Feature: Gestion de conductores
  Scenario: Listar conductores
    Given el usuario autenticado como administrador
    When navega a la seccion de conductores
    Then ve una tabla paginada con los conductores registrados
    And cada fila muestra cedula, nombres, licencia y estado

  Scenario: Crear conductor
    Given el usuario autenticado como administrador
    When completa el formulario con datos validos de un conductor
    Then el sistema guarda el registro
    And redirige al listado con el nuevo conductor visible

  Scenario: Editar conductor
    Given existe un conductor registrado
    When el usuario modifica sus datos en el formulario de edicion
    Then el sistema actualiza el registro

  Scenario: Desactivar conductor
    Given existe un conductor activo
    When el usuario confirma la desactivacion
    Then el conductor queda marcado como inactivo
    And ya no aparece en busquedas activas
```
