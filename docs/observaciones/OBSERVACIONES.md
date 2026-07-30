# Bitácora de Observaciones — Entregas 1A y 1B

Formato de cada entrada:

| Campo | Descripción |
|---|---|
| **Código** | OBS-01, OBS-02, ... |
| **Fuente** | Entrega 1A o 1B |
| **Criterio afectado** | Bloque / rúbrica afectada |
| **Texto íntegro** | Observación textual del docente |
| **Decisión del equipo** | Acción tomada |
| **Commit hash** | Hash donde se resolvió |

---

## OBS-01

| Campo | Valor |
|---|---|
| **Fuente** | Entrega 1A |
| **Criterio afectado** | C5 — Wireframes |
| **Texto íntegro** | Solo 2 wireframes (Login, Dashboard); la directriz exige mínimo 4. |
| **Decisión del equipo** | Se agregaron wireframes de las pantallas de Conductores, Usuarios, Rutas y Reportes en docs/prototipo/ |
| **Commit hash** | `—` |

## OBS-02

| Campo | Valor |
|---|---|
| **Fuente** | Entrega 1A |
| **Criterio afectado** | C7 — Calidad / coherencia / repositorio |
| **Texto íntegro** | 15 commits, pero SOLO 2 de 3 integrantes aportan: Tejada/Alxjandr07 = 9 y M. Escudero = 6; CASTRO ESPINOZA KEVIN SIN COMMITS. |
| **Decisión del equipo** | Se distribuyeron tareas para que Kevin realice commits de base de datos, entidades y documentación; commits verificables en adelante. |
| **Commit hash** | `—` |

## OBS-03

| Campo | Valor |
|---|---|
| **Fuente** | Entrega 1A |
| **Criterio afectado** | C2 — Referencias |
| **Texto íntegro** | La estadística del 40% se cita como "(Sociedad et al., 2018)" en el PDF y "(García et al., 2018)" en el README; la referencia final "Sociedad, U. Y., ... (2018)" es un volcado APA mal formado. El "Informe ANT 2022" no se pudo verificar. |
| **Decisión del equipo** | Se unificaron las citas al formato APA 7ª ed. y se corrigió la referencia de "Sociedad et al." y "García et al."; se agregó el enlace verificable del Informe ANT 2022. |
| **Commit hash** | `—` |

## OBS-04

| Campo | Valor |
|---|---|
| **Fuente** | Entrega 1A |
| **Criterio afectado** | C4 — Modelo de base de datos |
| **Texto íntegro** | Conductor.telefono e Incidente.nivel_sugerido figuran nulos en el diccionario pero NOT NULL en el DDL; id_novedad de Alerta está en el diccionario pero no en el CREATE TABLE. |
| **Decisión del equipo** | Se sincronizó el diccionario de datos con el DDL: Conductor.telefono e Incidente.nivel_sugerido se marcaron como NULL en la tabla; se agregó id_novedad al CREATE TABLE de Alerta. |
| **Commit hash** | `—` |

## OBS-05

| Campo | Valor |
|---|---|
| **Fuente** | Entrega 1B |
| **Criterio afectado** | C1 — Diagramas UML + DER |
| **Texto íntegro** | Completar las secciones del informe correspondientes al modelo C4 (Nivel 2) y al diagrama de clases UML, que aparecen como títulos sin contenido. |
| **Decisión del equipo** | Se completaron los diagramas C4 Nivel 2 (Structurizr DSL) y el diagrama de clases UML con las entidades actuales. |
| **Commit hash** | `—` |

## OBS-06

| Campo | Valor |
|---|---|
| **Fuente** | Entrega 1B |
| **Criterio afectado** | C4 — Seguridad OWASP |
| **Texto íntegro** | Elevar la seguridad a >=6 controles con evidencia: añadir autorización por rol con @PreAuthorize, cabeceras HTTP de seguridad y CORS explícito documentados. |
| **Decisión del equipo** | Se implementó @PreAuthorize en los endpoints críticos, cabeceras HTTP de seguridad (HSTS, CSP, X-Frame-Options, X-Content-Type-Options) y CORS explícito en SecurityConfig. |
| **Commit hash** | `—` |

## OBS-07

| Campo | Valor |
|---|---|
| **Fuente** | Entrega 1B |
| **Criterio afectado** | C5 — Postman |
| **Texto íntegro** | Incorporar la colección Postman al repositorio (no solo capturas). |
| **Decisión del equipo** | Se agregó la colección Postman en docs/postman/coleccion.json con 20+ requests cubriendo casos de éxito, error 401, 403, 404 y 422. |
| **Commit hash** | `—` |

## OBS-08

| Campo | Valor |
|---|---|
| **Fuente** | Entrega 1B |
| **Criterio afectado** | C6 — Métricas de rendimiento |
| **Texto íntegro** | Implementar y reportar las métricas de rendimiento (promedio y P95 con y sin Redis) y el speedup; en el informe figuran como trabajo futuro. |
| **Decisión del equipo** | Se ejecutaron 3 corridas de k6 con 50 VUs durante 30s, registrando p95, throughput y speedup con/sin caché; reporte en docs/mediciones/perf/. |
| **Commit hash** | `—` |
