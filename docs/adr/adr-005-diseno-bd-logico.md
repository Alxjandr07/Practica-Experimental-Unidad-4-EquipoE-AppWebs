# ADR-005: Diseno de Base de Datos con Soft Delete y Relaciones por Entidad

**Estado:** Aceptado

**Contexto:** El modelo de datos requiere conductores, vehiculos, rutas, asignaciones (relacion muchos-a-muchos) e incidentes. Se necesita integridad referencial y trazabilidad de eliminaciones.

**Decision:** Se disena el esquema con:
- Soft delete (columna `activo` booleana) en todas las tablas.
- Llaves foraneas con `ON DELETE RESTRICT`.
- Tabla `asignacion_rutas` como entidad polimorfica que relaciona conductores, vehiculos y rutas.
- Timestamps `creado_en` y `actualizado_en` en cada tabla con trigger de actualizacion automatica.

**Consecuencias:**
- **Positivas:** Trazabilidad completa de eliminaciones. Integridad referencial garantizada por BD. Triggers evitan errores de programacion en `actualizado_en`.
- **Negativas:** Las consultas deben filtrar siempre por `activo = true`. Las tablas crecen en tamano aunque los registros esten "eliminados". No hay cascada automatica de soft delete.
- **Riesgos:** Olvidar el filtro `activo = true` en una consulta retornaria datos "eliminados".

**Opciones consideradas:**
1. Soft delete con columna `activo` (seleccionado)
2. Hard delete (descartado por perdida de trazabilidad)
3. Tablas de auditoria separadas (descartado por complejidad inicial)
