# ADR-004: Cache Distribuido con Redis

**Estado:** Aceptado

**Contexto:** Los endpoints de listado de conductores, vehiculos, rutas y asignaciones son consultados frecuentemente. Se requiere reducir la carga en PostgreSQL y mejorar los tiempos de respuesta.

**Decision:** Se implementa Redis como cache de segundo nivel para datos de consulta frecuente (listados paginados). Spring Cache abstraction con `@Cacheable` y `@CacheEvict` sobre los servicios.

**Consecuencias:**
- **Positivas:** Reduccion de latencia en lecturas (de ~20ms a ~2ms). Menor carga en PostgreSQL. Invalidacion automatica de cache al crear/actualizar/eliminar.
- **Negativas:** Dependencia adicional (Redis debe estar disponible). Datos eventualmente inconsistentes entre cache y BD. Mayor consumo de memoria.
- **Riesgos:** Redis como punto unico de fallo. Se mitiga con configuracion de TTL y comportamiento graceful degradation (consultar BD si Redis falla).

**Opciones consideradas:**
1. Redis con Spring Cache (seleccionado)
2. Cache en memoria Caffeine (descartado por no compartirse entre instancias)
3. Sin cache (descartado por rendimiento suboptimo)
