# ADR-001: Arquitectura Monolitica con Spring Boot

**Estado:** Aceptado

**Contexto:** El sistema SGROAS debe ser desarrollado por un equipo pequeno con recursos limitados. Se requiere una plataforma de gestion de rutas, conductores, vehiculos e incidentes con una sola base de datos transaccional.

**Decision:** Se adopta una arquitectura monolítica con Spring Boot 3.5 + Java 21. La aplicacion se empaqueta como un unico JAR desplegable y utiliza una base de datos PostgreSQL compartida.

**Consecuencias:**
- **Positivas:** Simplicidad de desarrollo, despliegue y monitoreo. Un solo equipo puede manejar toda la funcionalidad. Menor sobrecarga de red y comunicacion entre servicios.
- **Negativas:** Escalabilidad limitada a nivel de componente. Un cambio en cualquier modulo requiere recompilar y redesplegar toda la aplicacion. Acoplamiento a largo plazo puede dificultar la separacion en microservicios.
- **Riesgos:** Si el sistema crece significativamente, la migracion a microservicios requerira refactorizacion mayor.

**Opciones consideradas:**
1. Arquitectura monolitica (seleccionada)
2. Microservicios con Spring Cloud (descartado por complejidad inicial)
