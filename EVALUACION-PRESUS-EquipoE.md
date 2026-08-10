# Evaluación del Proyecto del Compañero — PRESUS (Evaluación cruzada · Equipo E)

> **Actividad:** Práctica Experimental Unidad IV — Aplicaciones Web (UTEQ, 5to Nivel A).
> **Proyecto evaluado:** **PRESUS** — Sistema de Gestión de Pre-Sustentaciones de la UTEQ.
> **Evaluador:** Tejada Bajaña Luis Alejandro (revisión de la rama `PresusWeb`).
> **Formato:** evaluación con rúbrica y calificación por criterio, "como si estuviera calificando".
> **Total, máximo:** 100 puntos.

---

## 1. Datos generales del proyecto evaluado

| Atributo | Valor |
|---|---|
| Nombre | PRESUS — Sistema Gestión Pre-Sustentaciones UTEQ |
| Ubicación en el repo | `ProyectPresustentaciones  G AMZ/` (rama `PresusWeb`) |
| Backend | Spring Boot 3.2.1, Java 17, Maven |
| Frontend | Angular 21.1 (SPA, TypeScript, Vitest) |
| Base de datos | PostgreSQL 15 (Flyway + SQL manual) |
| Seguridad | Spring Security 6 + JWT (JJWT 0.12.5), BCrypt, roles |
| API | 21 controladores REST, OpenAPI/Swagger (springdoc 2.3.0) |
| Complementos | iText7 (PDF actas/reportes), JavaMail (SMTP), 4 SPs PL/pgSQL |

---

## 2. Rúbrica de calificación

### C1. Estructura MVC y arquitectura (10 pts) — **Nota: 8.0/10**

Se verifica una arquitectura N-Capas correcta (Controller → Service → Repository) con 21 controladores REST y 40
entidades JPA bien modeladas (Solicitud, Anteproyecto, Cronograma, Acta, Evaluacion, Tutoria, Jurado, Tutor, Sala,
Rubrica). El dominio es complejo y está bien descompuesto.

**Descuentos:**
- No hay `DispatcherServlet`/front-controller evidenciado en el informe con código real (solo descripción).
- El `docker-compose.yml` no despliega la aplicación (solo BD y Redis), por lo que el ciclo MVC no es verificable en
  ejecución con el Compose.

### C2. API REST — Principios de Fielding y URIs (15 pts) — **Nota: 10.5/15**

**Cumple:** sustantivos en URIs (`/api/solicitudes`, `/api/anteproyectos`, `/api/cronogramas`), verbos HTTP semánticos,
client-server claro, stateless con JWT, Swagger UI accesible (`/swagger-ui.html`) y colección Postman.

**Descuentos:**
- **No hay versionado `/api/v1/`** (ruta base `/api`). ✅ *requisito explícito de la guía*.
- Respuestas no usan el formato unificado `{success, data, message, errors, meta}`; el `GlobalExceptionHandler`
  devuelve `{mensaje}` simple.
- **Code-On-Demand / Layered System:** no documentado el análisis de los 6 principios contra la API (solo descripción
  genérica en el informe).
- La colección Postman incluye **endpoints que no existen** en el backend (`POST /api/jurados/asignar-masivo`,
  `GET /api/reportes/defensas`, `GET /api/anteproyectos`), lo que resta confiabilidad a la evidencia.

### C3. JWT y seguridad de autenticación (10 pts) — **Nota: 6.0/10**

**Cumple:** JWT HMAC-SHA256, BCrypt, cookie HttpOnly, `@PreAuthorize` por roles, CORS acotado,
`SessionCreationPolicy.STATELESS`, CSRF desactivado correctamente para API.

**Descuentos críticos:**
- JWT con **solo 3 claims** (`sub`, `iat`, `exp`); la guía pide los claims RFC 7519 (`iss`, `aud`, `nbf`, `jti`) y
  rol explícito.
- **Sin refresh token, sin blacklist en Redis y sin rate limiting** en el login (la guía pide resolución del
  problema de revocación JWT y estrategias con refresh/blacklist).
- Cookie JWT `Secure=false` y `SameSite=Lax` (deberían ser `Secure` + `SameSite=Strict`).
- **Falta `application.properties` en el repo** (gitignore): la aplicación no arranca sin configuración externa, lo
  que dificulta la reproduducibilidad para la defensa.

### C4. OpenAPI / Swagger y colección Postman (10 pts) — **Nota: 8.0/10**

**Cumple:** springdoc-openapi 2.3.0, `OpenApiConfig` con esquema `bearerAuth`, Swagger UI y JSON en `/v3/api-docs`,
colección Postman exportada (7 carpetas, ~18 requests).

**Descuentos:**
- Número de endpoints < 20 requeridos y varios requests de la colección no existen en los controladores.
- No se muestra en el informe el detalle request/response (schemas) de cada endpoint (la guía pide documentar con
  schemas).

### C5. Consumo de API externa con caché y gestión de errores (20 pts) — **Nota: 2.0/20**

**Este es el criterio más crítico y NO está cumplido.**

- **No existe ningún consumo de API REST externa** (sin `WebClient`/`RestClient`/`RestTemplate`/OkHttp). ✅
- **No existe caché Redis operativa en el backend:** `docker-compose` levanta Redis, pero `pom.xml` **no incluye**
  `spring-boot-starter-data-redis` y no hay `@Cacheable`/`@CacheEvict`. ✅
- No hay gestión de errores de red (timeout, 4xx/5xx) ni reintentos con backoff.
- **Contradicción documental:** `docs/usabilidad/Informe_Proy_avance3.{tex,md}` y algunos ADR declaran "caché Redis
  cumplido", pero no hay código que lo respalde; la propia `AUTOEVALUACION-PRESUS.md` (E1, E2) lo admite como **falta**.

### C6. Seguridad OWASP (10 pts) — **Nota: 6.5/10**

**Cumple:** JWT+BCrypt, consultas parametrizadas (JPA/SPs), validación Bean Validation, roles con `@PreAuthorize`,
CORS estricto. `docs/seguridad/OWASP-AUDIT.md` describe A01–A06.

**Descuentos:**
- **Sin cabeceras** HSTS, `X-Frame-Options`, `X-Content-Type-Options`, `Content-Security-Policy` ni `nosniff`.
- **Sin evidencia cruda** de las auditorías (scripts de dependency-check / OWASP ZAP / SpotBugs no adjuntos; solo
  salidas descritas).
- No cubre A07 (fallos de autenticación) ni XSS explícitos.

### C7. Pruebas de carga (10 pts) — **Nota: 7.0/10**

**Cumple:** 3 corridas k6 documentadas (`docs/pruebas/k6/`) — hasta 50 VUs, p95 ~174–188 ms (umbral < 500 ms), 0 %
de errores.

**Descuentos:**
- El script k6 prueba `/catalogos/carreras` y `/auth/login`; `/catalogos/carreras` **no existe** en el backend.
- No hay evidencia de Apache Bench (`ab`) como pide explícitamente la guía.
- Sin umbrales declarados con *thresholds* de la guía (p95/req-s/error) en un archivo de configuración revisable.

### C8. Docker Compose de producción (10 pts) — **Nota: 4.0/10**

- Existe `docker-compose.yml` con `postgres:15-alpine` (healthcheck) y `redis:7-alpine`.
- **No hay Dockerfile** (verificado: 0 resultados de glob `**/Dockerfile*`), **no hay servicio backend, ni frontend,
  ni nginx**, y no hay variables de entorno para la app. La guía pide la pila `(app, mysql, redis, nginx)`.
- ADR-006 y OBS-05 prometen anclaje sha256 y backend/nginx **no implementados**.
- `make up` solo levanta BD y Redis; la aplicación no se levanta con Compose.

### C9. Pruebas automatizadas y cobertura (5 pts) — **Nota: 1.5/5**

- Backend: **3 archivos de prueba, ~7 casos** (contextLoads, JwtTokenProvider 2, UsuarioServiceImpl 4).
- Frontend: 2 tests smoke.
- **Sin cumplir** el mínimo de 10 pruebas de feature/integración de la guía.
- La matriz de trazabilidad menciona tests (SolicitudService, CronogramaService, ActaServiceImpl, etc.) **que no
  existen** en `backend/src/test`.
- JaCoCo configurado sin umbral y sin reporte de cobertura guardado.

### C10. Documentación, trazabilidad y evidencias (10 pts) — **Nota: 8.5/10**

**Puntos fuertes (documentación sobresaliente):** SRS ISO/IEC/IEEE 29148, 6 ADRs, diagramas C4 (N1–N3), OWASP audit,
k6, Lighthouse (Perf 94 / Acc 98 / BP 96 / SEO 95), SUS 91.25 (grado A+), catálogo de 4 SPs, matriz de trazabilidad
RF→HU→módulo→endpoint→test→evidencia, ética (consentimientos E1–E10) y DOI Zenodo.

**Descuentos:**
- Inconsistencia documental: ADR-001/004 dicen "Angular 17" pero el código es Angular 21.1; informe de usabilidad
  declara caché "cumplido" sin código; Postman/matriz referencian endpoints inexistentes.
- Los 4 procedimientos almacenados PL/pgSQL están definidos y catalogados, **pero no son invocados desde la
  aplicación** (0 referencias `sp_` en `src`), pese a que el informe los presenta como funcionales.

---

## 3. Tabla de calificación consolidada

| Criterio | Peso máximo | Nota obtenida |
|---|---|---|
| C1. Estructura MVC y arquitectura | 10 | 8,0 |
| C2. API REST — Fielding y URIs | 15 | 10,5 |
| C3. JWT y seguridad de autenticación | 10 | 6,0 |
| C4. OpenAPI / Swagger y Postman | 10 | 8,0 |
| C5. Consumo de API externa con caché | 20 | 2,0 |
| C6. Seguridad OWASP | 10 | 6,5 |
| C7. Pruebas de carga | 10 | 7,0 |
| C8. Docker Compose de producción | 10 | 4,0 |
| C9. Pruebas automatizadas y cobertura | 5 | 1,5 |
| C10. Documentación y trazabilidad | 10 | 8,5 |
| **TOTAL** | **100** | **62,0 / 100** |

---

## 4. Puntos fuertes destacados

1. **Dominio y modelo de datos maduro:** 40 entidades y 21 controladores que cubren un flujo completo
   (solicitud → anteproyecto → tutorías → cronograma → jurado → evaluación → acta), algo raro en proyectos de aula.
2. **Generación real de PDF con iText7** (actas, reportes, estadísticas) y **firma digital de actas por varios
   actores** — funcionalidad diferenciadora.
3. **Seguridad base correcta:** JWT + BCrypt + roles con `@PreAuthorize` + CORS acotado.
4. **Documentación excepcional:** SRS, ADR, C4, Lighthouse 94+, SUS 91.25 (grado A+), matriz de trazabilidad y DOI
   Zenodo — nivel muy superior al promedio.
5. **Autoevaluación honesta** (`AUTOEVALUACION-PRESUS.md`, E1–E10): reconoce con claridad lo que falta; es el
   documento más confiable de todo el repositorio.

## 5. Brechas críticas a corregir (retroalimentación)

| # | Prioridad | Acción recomendada |
|---|---|---|
| B1 | 🔴 Alta | Implementar **consumo de API externa** (WebClient/RestClient) con timeouts, manejo 4xx/5xx y reintentos con backoff; mostrar el dato consumido en la UI. |
| B2 | 🔴 Alta | Activar **caché Redis real** en el backend: agregar `spring-boot-starter-data-redis`, `@Cacheable("api_externa")` con TTL por dominio y verificar con `redis-cli KEYS "*api_externa*"`. |
| B3 | 🔴 Alta | **Versionar** la API (`/api/v1/`) y unificar respuestas con el formato `{success, data, message, errors, meta}`. |
| B4 | 🟠 Media | Completar JWT: agregar claims `iss`, `aud`, `nbf`, `jti` y rol; añadir **refresh token + blacklist** y **rate limit** en login; cookie `Secure=true` + `SameSite=Strict`. |
| B5 | 🟠 Media | Añadir **Dockerfile** y servicios `backend` y `nginx` al Compose; sacar `application.properties` del gitignore o proveer `.env.example` funcional. |
| B6 | 🟠 Media | Subir cobertura de pruebas: mínimo 10 tests de feature/integración reales (los que ya cita la matriz) y reporte JaCoCo con umbral. |
| B7 | 🟡 Baja | Alinear la documentación: Angular 21.1 (no 17), quitar endpoints inexistentes de Postman/fuera de la matriz, e invocar realmente los SPs desde repositorios o declararlos pendientes. |

## 6. Veredicto final

El proyecto PRESUS tiene **calidad documental y de modelado de dominio sobresalientes** (lo mejor de la evaluación),
una base de seguridad razonable y un flujo funcional completo. Sin embargo, **no cumple el criterio específico más
importante de la Práctica Experimental de la Unidad IV: el consumo de una API REST externa con caché y gestión de
errores (Paso 3, OE3)** — que vale 20 de 100 puntos — ni alcanza el mínimo de pruebas de la guía.

**Calificación final: 62,0 / 100 → Suficiente (con las brechas críticas B1–B3 por cerrar antes de la defensa).**
Con la corrección de B1–B3 y B5 (Docker completo), el proyecto alcanza fácilmente el rango de 80–90, dado que su
documentación y dominio ya son consistentes.