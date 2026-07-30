# CHANGELOG de Requisitos — SGROAS

## Entrega 2 — Nuevas entidades, reportes y documentacion

### Agregado

- **REQ-006:** CRUD completo de vehiculos (VehiculoController, VehiculoService, VehiculoRepository)
- **REQ-007:** CRUD completo de rutas (RutaController, RutaService, RutaRepository)
- **REQ-008:** CRUD completo de asignaciones ruta-conductor-vehiculo (AsignacionRutaController, AsignacionRutaService, AsignacionRutaRepository)
- **REQ-009:** CRUD completo de incidentes (IncidenteController, IncidenteService, IncidenteRepository)
- **REQ-010:** Stored Procedure `sp_obtener_incidentes_por_rango` — reporte de incidentes por rango de fechas
- **REQ-011:** Stored Procedure `sp_incidentes_por_gravedad` — agregacion de incidentes por gravedad
- **REQ-012:** Stored Procedure `sp_asignaciones_activas_por_conductor` — asignaciones activas por conductor
- **REQ-013:** Stored Procedure `sp_vehiculos_en_mantenimiento` — listado de vehiculos en mantenimiento
- **REQ-014:** Stored Procedure `sp_reporte_rendimiento_rutas` — rendimiento por ruta con metricas
- **REQ-015:** Stored Procedure `fn_licencias_por_vencer` — alerta de licencias proximas a vencer
- **REQ-016:** Stored Procedure `fn_estadisticas_generales` — dashboard de KPIs del sistema
- **REQ-019:** Funcion de licencias por vencer (integrada con fn_licencias_por_vencer)
- **REQ-020:** Migraciones Flyway V3 (nuevas tablas) y V4 (datos semilla)

### Documentacion

- `docs/basedatos/CATALOGO-SP.md` — Catalogo completo de stored procedures
- `docs/adr/adr-001.md` al `adr-006.md` — 6 ADRs formato Nygard
- `docs/mediciones/DATA-DICTIONARY.md` — Actualizado con nuevas entidades y endpoints
- `docs/trazabilidad/matriz.csv` — Matriz de trazabilidad requisitos vs implementacion
- `docs/requisitos/CHANGELOG-REQ.md` — Este archivo

### Cambios en Base de Datos

- Nueva tabla: `vehiculos` con indices, constraints y trigger
- Nueva tabla: `rutas` con indices, constraints y trigger
- Nueva tabla: `asignacion_rutas` con FK a conductores, vehiculos y rutas
- Nueva tabla: `incidentes` con FK a asignacion_rutas y tipos enumerados
- 7 stored procedures/functions para reportes y agregaciones
- Datos semilla para todas las nuevas tablas

### Cambios en API

- `POST/GET /api/vehiculos` — CRUD vehiculos
- `POST/GET /api/rutas` — CRUD rutas
- `POST/GET /api/asignaciones` — CRUD asignaciones
- `POST/GET /api/incidentes` — CRUD incidentes
