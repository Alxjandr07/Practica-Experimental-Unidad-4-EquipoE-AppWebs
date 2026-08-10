# SGROAS - Sistema de Gestión de Recursos Operativos, Administrativos y de Seguridad

[![CI](https://github.com/Alxjandr07/SGROAS-ProyectoAppWeb/actions/workflows/ci.yml/badge.svg)](https://github.com/Alxjandr07/SGROAS-ProyectoAppWeb/actions/workflows/ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-21-blue)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-green)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Angular-17-red)](https://angular.io/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-336791)](https://www.postgresql.org/)
[![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.21698129.svg)](https://doi.org/10.5281/zenodo.21698129)

> **Grupo D** — Universidad Técnica Estatal de Quevedo (UTEQ) — FCC — Carrera de Ingeniería de Software
> Aplicaciones Web — Quinto Nivel — Periodo 2026-2027

## Actividad combinada: PFC + Práctica Experimental Unidad IV

Este repositorio corresponde a una **actividad combinada** entre el **Proyecto Fin de Curso (PFC)** y la
**Práctica Experimental de la Unidad IV** de la asignatura Aplicaciones Web. El proyecto evaluado es **SGROAS**
(sistema de gestión de recursos operativos, administrativos y de seguridad de una cooperativa de transporte
interprovincial).

Como parte de la dinámica, los integrantes realizarán una **retroalimentación cruzada**: cada miembro revisa el
proyecto, identifica fortalezas y brechas, y propone mejoras. Para documentar ese proceso se incluyen:

- 📄 **[Informe técnico de la Práctica Unidad IV](Informe-UNIDAD-4-SGROAS/Informe_Unidad4_SGROAS.tex)** — fundamento
  teórico completo (MVC, APIs REST/Fielding, JWT, SOAP, seguridad OWASP, pruebas de carga y Docker) junto con la
  versión editable en [Word](Informe-UNIDAD-4-SGROAS/Informe_Unidad4_SGROAS.docx).
- 🔎 **[Autoevaluación del proyecto](Informe-UNIDAD-4-SGROAS/AUTOEVALUACION-SGROAS.md)** — análisis de las cosas que
  faltan y que deberían mejorar en SGROAS para cumplir los criterios de la práctica, con plan de mejora y prioridades.
- 🧑‍🏫 **[Evaluación del proyecto del compañero (PRESUS)](EVALUACION-PRESUS-EquipoE.md)** — rúbrica con calificación
  por criterio del proyecto PRESUS (rama `PresusWeb`), puntos fuertes, brechas críticas y retroalimentación para la
  defensa.

## Integrantes

| Integrante | Rol |
|---|---|
| Kevin Moisés Castro Espinoza | Desarrollador Backend |
| María del Rosario Escudero Plaza | Desarrolladora Frontend / Documentación |
| Luis Alejandro Tejada Bajaña | Desarrollador Backend / Infraestructura |

## Arranque rápido

```bash
# Clonar
git clone https://github.com/Alxjandr07/SGROAS-ProyectoAppWeb.git
cd SGROAS-ProyectoAppWeb

# Copiar variables de entorno
cp .env.example .env

# Levantar todo
make up

# Ejecutar pruebas
make test

# Benchmarks
make bench

# Auditoría de seguridad
make audit

# Limpiar
make down
```

Sistema disponible en `http://localhost:8080`.

### Credenciales por defecto

| Usuario | Rol | Contraseña |
|---|---|---|
| admin@sgroas.com | ADMIN | admin123 |
| coordinador@sgroas.com | COORDINADOR | coord123 |
| seguridad@sgroas.com | SEGURIDAD | seg123 |

## Estructura del repositorio

```
.
├── backend/          # Spring Boot 3.5 / Java 21
├── frontend/         # Angular 17+
├── db/               # Schema, seed, stored procedures
├── docs/             # Documentación completa
├── k6/               # Benchmarks de rendimiento
├── scripts/          # Utilidades de validación
└── .github/          # CI/CD
```

## Licencia

Distribuido bajo licencia MIT. Ver [LICENSE](LICENSE).

## Citación

```bibtex
@software{sgroas_2026,
  author = {Castro Espinoza, Kevin Moisés and Escudero Plaza, María del Rosario and Tejada Bajaña, Luis Alejandro},
  title = {SGROAS: Sistema de Gestión de Recursos Operativos, Administrativos y de Seguridad},
  month = jul,
  year = 2026,
  publisher = {Zenodo},
  doi = {10.5281/zenodo.XXXXXXX},
  url = {https://github.com/Alxjandr07/SGROAS-ProyectoAppWeb}
}
```
