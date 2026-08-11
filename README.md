# PRESUS — Sistema de Gestión de Pre-Sustentaciones UTEQ

> **Universidad Técnica Estatal de Quevedo (UTEQ) — FCC — Carrera de Ingeniería de Software**
> Aplicaciones Web — Quinto Nivel — Período 2026-2027
> **Rama de trabajo:** `PresusWeb`

## 🎓 Actividad combinada: PFC + Práctica Experimental Unidad IV

Este repositorio corresponde a una **actividad combinada** entre el **Proyecto Fin de Curso (PFC)** y la
**Práctica Experimental de la Unidad IV** de la asignatura Aplicaciones Web. Aquí se desarrolla el proyecto
**PRESUS**: el sistema de gestión de pre-sustentaciones de trabajos de titulación de la UTEQ.

Como parte de la dinámica, los integrantes realizan una **retroalimentación cruzada**: cada miembro revisa el
proyecto de su compañero, identifica fortalezas y brechas, y propone mejoras.

## 📥 Lo que debe contener esta rama

> Marca con ✅ lo que ya esté subido y agrega los enlaces directos de cada documento.

- 📄 **[Informe técnico de la Práctica Unidad IV](ProyectPresustentaciones  G AMZ/Informe-UNIDAD-4-PRESUS/Informe_Unidad4_PRESUS.tex)** — fundamento teórico completo de la Unidad IV (MVC, APIs REST/Fielding, JWT, SOAP, seguridad OWASP, pruebas de carga y Docker) junto con la versión editable en [Word](ProyectPresustentaciones  G AMZ/Informe-UNIDAD-4-PRESUS/Informe_Unidad4_PRESUS.docx).
- 🔎 **[Autoevaluación del proyecto PRESUS](ProyectPresustentaciones  G AMZ/Informe-UNIDAD-4-PRESUS/AUTOEVALUACION-PRESUS.md)** — análisis de las cosas que faltan y que deberían mejorar en PRESUS, con plan de mejora y prioridades.
- 🧑‍🏫 **[Evaluación del proyecto del compañero (SGROAS)](ProyectPresustentaciones  G AMZ/EVALUACION-SGROAS-Por-JeanPierre.md)** — autoevaluación/retroalimentación con diagnóstico del proyecto SGROAS de tu compañero Alejandro Tejada (estado del frontend, brechas funcionales y recomendaciones frente a la guía PE-U4).
- 📄 **[Evaluación recibida — críticas a PRESUS](ProyectPresustentaciones  G AMZ/EVALUACION-PRESUS-EquipoE.md)** — rúbrica con calificación por criterio de PRESUS realizada por tu compañero Alejandro Tejada.

## 🚀 Tecnologías

- **Backend:** Java 17 · Spring Boot 3.2.1 · PostgreSQL 15 · Flyway · Spring Security + JWT · OpenAPI/Swagger · iText (PDF)
- **Frontend:** Angular 21 · TypeScript · Vitest

## ⚙️ Arranque rápido

```bash
# Backend (en la carpeta del proyecto)
cd "ProyectPresustentaciones  G AMZ/backend"
mvn spring-boot:run

# Frontend
cd "ProyectPresustentaciones  G AMZ/Frontend"
npm install
ng serve
```

Swagger UI: `http://localhost:8080/swagger-ui.html`

## 📁 Estructura del proyecto

```
ProyectPresustentaciones  G AMZ/
├── backend/                 # Spring Boot 3.2.1 / Java 17
├── Frontend/                # Angular 21
├── db/                      # Migraciones y scripts SQL
├── docs/                    # SRS, ADR, C4, seguridad, usabilidad, pruebas
├── Informe-UNIDAD-4-PRESUS/ # Informe LaTeX + Word + autoevaluación
└── docker-compose.yml       # PostgreSQL 15 + Redis
```