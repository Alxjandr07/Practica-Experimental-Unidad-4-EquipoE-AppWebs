# ADR-002: JPA + Hibernate con Flyway para Persistencia

**Estado:** Aceptado

**Contexto:** Se necesita un ORM para mapear objetos Java a tablas PostgreSQL y un sistema de migraciones de esquema que garantice consistencia entre entornos.

**Decision:** Se utiliza JPA + Hibernate como ORM con Flyway para migraciones. Hibernate se configura con `ddl-auto=validate` para que solo Flyway gestione el esquema.

**Consecuencias:**
- **Positivas:** Esquema versionado y reproducible en cualquier entorno. Validacion automatica de que el codigo Java coincide con el esquema de BD. Migraciones incrementales con rollback explicito.
- **Negativas:** Doble fuente de verdad temporal hasta que Flyway corre. Curva de aprendizaje de Flyway para nuevos desarrolladores. Las migraciones manuales en SQL requieren disciplina.
- **Riesgos:** Un desarrollador podria modificar entidades sin crear la migracion correspondiente, rompiendo `validate`.

**Opciones consideradas:**
1. JPA + Hibernate con Flyway (seleccionado)
2. Hibernate `ddl-auto=update` (descartado por riesgos en produccion)
3. MyBatis (descartado por menor productividad)
