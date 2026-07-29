# Changelog

Todas las cambios notables de SGROAS se documentan aquí.

Formato basado en [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
y el proyecto usa [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.9.0-rc] — 2026-07-24 — Tercera Entrega

### Añadido

- Estructura completa de documentación (`docs/`)
- README con badges, arranque rápido y credenciales
- LICENSE (MIT), CITATION.cff, CONTRIBUTORS.md (CRediT)
- CHANGELOG.md, VERSIONING.md
- Makefile con objetivos up, down, test, bench, audit, clean
- .env.example con defaults seguros
- Autenticación via Cookie HttpOnly + Secure + SameSite=Strict
- JWT con 7 claims estándar (iss, sub, aud, exp, nbf, iat, jti)
- Error handling con ProblemDetails (RFC 7807)
- OpenAPI 3.0 documentation en /api/docs
- Cache Redis en endpoint de listado con TTL y hit ratio
- Stored Procedures en db/procs/ con catálogo documentado
- Entidades: Vehiculo, Ruta, AsignacionRuta, Incidente
- Frontend Angular 17+
- Diagramas C4 (niveles 1, 2, 3) en Structurizr DSL
- 6 ADRs (plantilla Nygard)
- SRS conforme a ISO/IEC/IEEE 29148:2018
- Matriz de trazabilidad end-to-end
- Pruebas de rendimiento con k6 (3 corridas, 50 VUs, 30s)
- Auditoría OWASP Top 10 (A01, A02, A03, A05, A07, A09)
- Pruebas SUS con 10 participantes
- Cobertura JaCoCo >= 60%
- Lighthouse CI (Performance >= 80, Accessibility >= 90)
- CI/CD con GitHub Actions
- Colección Postman (20+ peticiones)
- Documentación ética (ETHICS.md, consentimiento informado)

### Cambiado

- Migración de auth Bearer token a Cookie-based
- Mejora de error handling a ProblemDetails RFC 7807

## [0.7.1] — 2026-07-XX

### Cerrado

- Aplicación de observaciones de Entregas 1A y 1B (ver docs/observaciones/OBSERVACIONES.md)

## [0.7.0] — 2026-06-14 — Entrega 1B

### Añadido

- API REST Spring Boot con endpoints CRUD de conductores
- Autenticación JWT (Bearer token)
- Base de datos PostgreSQL con Flyway
- Docker Compose (postgres, redis, backend)
- Pruebas unitarias con JUnit 5 + Mockito

## [0.3.0] — 2026-06-04 — Entrega 1A

### Añadido

- Ingeniería de requisitos inicial
- Documentación del proyecto
- Prototipos de interfaz
