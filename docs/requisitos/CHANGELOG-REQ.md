# Changelog de Requisitos — SGROAS

Formato basado en [Keep a Changelog](https://keepachangelog.com/en/1.1.0/).

## [v0.9.0-rc] — 2026-07-24

### Added

- REQ-F-010 a REQ-F-014: CRUD de usuarios (gestion de cuentas del sistema)
- REQ-F-005 a REQ-F-009: CRUD de conductores (gestion de flota vehicular)
- REQ-F-001 a REQ-F-004: Autenticacion JWT (login, register, refresh, logout)
- REQ-NF-001: Cabeceras de seguridad HTTP (HSTS, CSP, X-Frame-Options)
- REQ-NF-002: Cifrado TLS v1.3 con suites AEAD
- REQ-NF-003: Rendimiento con p95 < 200ms (cache caliente)
- REQ-NF-004: Proteccion contra inyeccion SQL
- REQ-NF-005: Rate limiting en login (6 intentos, 429)
- REQ-NF-006: Cobertura JaCoCo >= 60%

### Modified

- REQ-F-001: Migrado de Bearer token a cookie HttpOnly + Secure + SameSite=Strict
- REQ-NF-004: Reforzado con validacion Jakarta + ProblemDetails RFC 7807

### Removed

- Ninguno

## [v0.7.0] — 2026-06-14

### Added

- REQ-F-001 a REQ-F-004: Autenticacion JWT basica (Bearer token)
- REQ-F-005 a REQ-F-009: CRUD de conductores
- REQ-NF-003: Cache Redis en listado de conductores

## [v0.3.0] — 2026-06-04

### Added

- Requisitos iniciales del sistema (14 RF + 6 RNF)
- Definicion de actores y modulos del sistema
- Criterios de aceptacion iniciales
