# Evaluaci├│n del Proyecto del Compa├▒ero ÔÇö PRESUS (Evaluaci├│n cruzada ┬À Equipo E)

> **Actividad:** Pr├íctica Experimental Unidad IV ÔÇö Aplicaciones Web (UTEQ, 5to Nivel A).
> **Proyecto evaluado:** **PRESUS** ÔÇö Sistema de Gesti├│n de Pre-Sustentaciones de la UTEQ.
> **Evaluador:** Tejada Baja├▒a Luis Alejandro (revisi├│n de la rama `PresusWeb`).
> **Formato:** evaluaci├│n con r├║brica y calificaci├│n por criterio, "como si estuviera calificando".
> **Total, m├íximo:** 100 puntos.

---

## 1. Datos generales del proyecto evaluado

| Atributo | Valor |
|---|---|
| Nombre | PRESUS ÔÇö Sistema Gesti├│n Pre-Sustentaciones UTEQ |
| Ubicaci├│n en el repo | `ProyectPresustentaciones  G AMZ/` (rama `PresusWeb`) |
| Backend | Spring Boot 3.2.1, Java 17, Maven |
| Frontend | Angular 21.1 (SPA, TypeScript, Vitest) |
| Base de datos | PostgreSQL 15 (Flyway + SQL manual) |
| Seguridad | Spring Security 6 + JWT (JJWT 0.12.5), BCrypt, roles |
| API | 21 controladores REST, OpenAPI/Swagger (springdoc 2.3.0) |
| Complementos | iText7 (PDF actas/reportes), JavaMail (SMTP), 4 SPs PL/pgSQL |

---

## 2. R├║brica de calificaci├│n

### C1. Estructura MVC y arquitectura (10 pts) ÔÇö **Nota: 8.0/10**

Se verifica una arquitectura N-Capas correcta (Controller ÔåÆ Service ÔåÆ Repository) con 21 controladores REST y 40
entidades JPA bien modeladas (Solicitud, Anteproyecto, Cronograma, Acta, Evaluacion, Tutoria, Jurado, Tutor, Sala,
Rubrica). El dominio es complejo y est├í bien descompuesto.

**Descuentos:**
- No hay `DispatcherServlet`/front-controller evidenciado en el informe con c├│digo real (solo descripci├│n).
- El `docker-compose.yml` no despliega la aplicaci├│n (solo BD y Redis), por lo que el ciclo MVC no es verificable en
  ejecuci├│n con el Compose.

### C2. API REST ÔÇö Principios de Fielding y URIs (15 pts) ÔÇö **Nota: 10.5/15**

**Cumple:** sustantivos en URIs (`/api/solicitudes`, `/api/anteproyectos`, `/api/cronogramas`), verbos HTTP sem├ínticos,
client-server claro, stateless con JWT, Swagger UI accesible (`/swagger-ui.html`) y colecci├│n Postman.

**Descuentos:**
- **No hay versionado `/api/v1/`** (ruta base `/api`). Ô£à *requisito expl├¡cito de la gu├¡a*.
- Respuestas no usan el formato unificado `{success, data, message, errors, meta}`; el `GlobalExceptionHandler`
  devuelve `{mensaje}` simple.
- **Code-On-Demand / Layered System:** no documentado el an├ílisis de los 6 principios contra la API (solo descripci├│n
  gen├®rica en el informe).
- La colecci├│n Postman incluye **endpoints que no existen** en el backend (`POST /api/jurados/asignar-masivo`,
  `GET /api/reportes/defensas`, `GET /api/anteproyectos`), lo que resta confiabilidad a la evidencia.

### C3. JWT y seguridad de autenticaci├│n (10 pts) ÔÇö **Nota: 6.0/10**

**Cumple:** JWT HMAC-SHA256, BCrypt, cookie HttpOnly, `@PreAuthorize` por roles, CORS acotado,
`SessionCreationPolicy.STATELESS`, CSRF desactivado correctamente para API.

**Descuentos cr├¡ticos:**
- JWT con **solo 3 claims** (`sub`, `iat`, `exp`); la gu├¡a pide los claims RFC 7519 (`iss`, `aud`, `nbf`, `jti`) y
  rol expl├¡cito.
- **Sin refresh token, sin blacklist en Redis y sin rate limiting** en el login (la gu├¡a pide resoluci├│n del
  problema de revocaci├│n JWT y estrategias con refresh/blacklist).
- Cookie JWT `Secure=false` y `SameSite=Lax` (deber├¡an ser `Secure` + `SameSite=Strict`).
- **Falta `application.properties` en el repo** (gitignore): la aplicaci├│n no arranca sin configuraci├│n externa, lo
  que dificulta la reproduducibilidad para la defensa.

### C4. OpenAPI / Swagger y colecci├│n Postman (10 pts) ÔÇö **Nota: 8.0/10**

**Cumple:** springdoc-openapi 2.3.0, `OpenApiConfig` con esquema `bearerAuth`, Swagger UI y JSON en `/v3/api-docs`,
colecci├│n Postman exportada (7 carpetas, ~18 requests).

**Descuentos:**
- N├║mero de endpoints < 20 requeridos y varios requests de la colecci├│n no existen en los controladores.
- No se muestra en el informe el detalle request/response (schemas) de cada endpoint (la gu├¡a pide documentar con
  schemas).

### C5. Consumo de API externa con cach├® y gesti├│n de errores (20 pts) ÔÇö **Nota: 2.0/20**

**Este es el criterio m├ís cr├¡tico y NO est├í cumplido.**

- **No existe ning├║n consumo de API REST externa** (sin `WebClient`/`RestClient`/`RestTemplate`/OkHttp). Ô£à
- **No existe cach├® Redis operativa en el backend:** `docker-compose` levanta Redis, pero `pom.xml` **no incluye**
  `spring-boot-starter-data-redis` y no hay `@Cacheable`/`@CacheEvict`. Ô£à
- No hay gesti├│n de errores de red (timeout, 4xx/5xx) ni reintentos con backoff.
- **Contradicci├│n documental:** `docs/usabilidad/Informe_Proy_avance3.{tex,md}` y algunos ADR declaran "cach├® Redis
  cumplido", pero no hay c├│digo que lo respalde; la propia `AUTOEVALUACION-PRESUS.md` (E1, E2) lo admite como **falta**.

### C6. Seguridad OWASP (10 pts) ÔÇö **Nota: 6.5/10**

**Cumple:** JWT+BCrypt, consultas parametrizadas (JPA/SPs), validaci├│n Bean Validation, roles con `@PreAuthorize`,
CORS estricto. `docs/seguridad/OWASP-AUDIT.md` describe A01ÔÇôA06.

**Descuentos:**
- **Sin cabeceras** HSTS, `X-Frame-Options`, `X-Content-Type-Options`, `Content-Security-Policy` ni `nosniff`.
- **Sin evidencia cruda** de las auditor├¡as (scripts de dependency-check / OWASP ZAP / SpotBugs no adjuntos; solo
  salidas descritas).
- No cubre A07 (fallos de autenticaci├│n) ni XSS expl├¡citos.

### C7. Pruebas de carga (10 pts) ÔÇö **Nota: 7.0/10**

**Cumple:** 3 corridas k6 documentadas (`docs/pruebas/k6/`) ÔÇö hasta 50 VUs, p95 ~174ÔÇô188 ms (umbral < 500 ms), 0 %
de errores.

**Descuentos:**
- El script k6 prueba `/catalogos/carreras` y `/auth/login`; `/catalogos/carreras` **no existe** en el backend.
- No hay evidencia de Apache Bench (`ab`) como pide expl├¡citamente la gu├¡a.
- Sin umbrales declarados con *thresholds* de la gu├¡a (p95/req-s/error) en un archivo de configuraci├│n revisable.

### C8. Docker Compose de producci├│n (10 pts) ÔÇö **Nota: 4.0/10**

- Existe `docker-compose.yml` con `postgres:15-alpine` (healthcheck) y `redis:7-alpine`.
- **No hay Dockerfile** (verificado: 0 resultados de glob `**/Dockerfile*`), **no hay servicio backend, ni frontend,
  ni nginx**, y no hay variables de entorno para la app. La gu├¡a pide la pila `(app, mysql, redis, nginx)`.
- ADR-006 y OBS-05 prometen anclaje sha256 y backend/nginx **no implementados**.
- `make up` solo levanta BD y Redis; la aplicaci├│n no se levanta con Compose.

### C9. Pruebas automatizadas y cobertura (5 pts) ÔÇö **Nota: 1.5/5**

- Backend: **3 archivos de prueba, ~7 casos** (contextLoads, JwtTokenProvider 2, UsuarioServiceImpl 4).
- Frontend: 2 tests smoke.
- **Sin cumplir** el m├¡nimo de 10 pruebas de feature/integraci├│n de la gu├¡a.
- La matriz de trazabilidad menciona tests (SolicitudService, CronogramaService, ActaServiceImpl, etc.) **que no
  existen** en `backend/src/test`.
- JaCoCo configurado sin umbral y sin reporte de cobertura guardado.

### C10. Documentaci├│n, trazabilidad y evidencias (10 pts) ÔÇö **Nota: 8.5/10**

**Puntos fuertes (documentaci├│n sobresaliente):** SRS ISO/IEC/IEEE 29148, 6 ADRs, diagramas C4 (N1ÔÇôN3), OWASP audit,
k6, Lighthouse (Perf 94 / Acc 98 / BP 96 / SEO 95), SUS 91.25 (grado A+), cat├ílogo de 4 SPs, matriz de trazabilidad
RFÔåÆHUÔåÆm├│duloÔåÆendpointÔåÆtestÔåÆevidencia, ├®tica (consentimientos E1ÔÇôE10) y DOI Zenodo.

**Descuentos:**
- Inconsistencia documental: ADR-001/004 dicen "Angular 17" pero el c├│digo es Angular 21.1; informe de usabilidad
  declara cach├® "cumplido" sin c├│digo; Postman/matriz referencian endpoints inexistentes.
- Los 4 procedimientos almacenados PL/pgSQL est├ín definidos y catalogados, **pero no son invocados desde la
  aplicaci├│n** (0 referencias `sp_` en `src`), pese a que el informe los presenta como funcionales.

---

## 3. Tabla de calificaci├│n consolidada

| Criterio | Peso m├íximo | Nota obtenida |
|---|---|---|
| C1. Estructura MVC y arquitectura | 10 | 8,0 |
| C2. API REST ÔÇö Fielding y URIs | 15 | 10,5 |
| C3. JWT y seguridad de autenticaci├│n | 10 | 6,0 |
| C4. OpenAPI / Swagger y Postman | 10 | 8,0 |
| C5. Consumo de API externa con cach├® | 20 | 2,0 |
| C6. Seguridad OWASP | 10 | 6,5 |
| C7. Pruebas de carga | 10 | 7,0 |
| C8. Docker Compose de producci├│n | 10 | 4,0 |
| C9. Pruebas automatizadas y cobertura | 5 | 1,5 |
| C10. Documentaci├│n y trazabilidad | 10 | 8,5 |
| **TOTAL** | **100** | **62,0 / 100** |

---

## 4. Puntos fuertes destacados

1. **Dominio y modelo de datos maduro:** 40 entidades y 21 controladores que cubren un flujo completo
   (solicitud ÔåÆ anteproyecto ÔåÆ tutor├¡as ÔåÆ cronograma ÔåÆ jurado ÔåÆ evaluaci├│n ÔåÆ acta), algo raro en proyectos de aula.
2. **Generaci├│n real de PDF con iText7** (actas, reportes, estad├¡sticas) y **firma digital de actas por varios
   actores** ÔÇö funcionalidad diferenciadora.
3. **Seguridad base correcta:** JWT + BCrypt + roles con `@PreAuthorize` + CORS acotado.
4. **Documentaci├│n excepcional:** SRS, ADR, C4, Lighthouse 94+, SUS 91.25 (grado A+), matriz de trazabilidad y DOI
   Zenodo ÔÇö nivel muy superior al promedio.
5. **Autoevaluaci├│n honesta** (`AUTOEVALUACION-PRESUS.md`, E1ÔÇôE10): reconoce con claridad lo que falta; es el
   documento m├ís confiable de todo el repositorio.

## 5. Brechas cr├¡ticas a corregir (retroalimentaci├│n)

| # | Prioridad | Acci├│n recomendada |
|---|---|---|
| B1 | ­ƒö┤ Alta | Implementar **consumo de API externa** (WebClient/RestClient) con timeouts, manejo 4xx/5xx y reintentos con backoff; mostrar el dato consumido en la UI. |
| B2 | ­ƒö┤ Alta | Activar **cach├® Redis real** en el backend: agregar `spring-boot-starter-data-redis`, `@Cacheable("api_externa")` con TTL por dominio y verificar con `redis-cli KEYS "*api_externa*"`. |
| B3 | ­ƒö┤ Alta | **Versionar** la API (`/api/v1/`) y unificar respuestas con el formato `{success, data, message, errors, meta}`. |
| B4 | ­ƒƒá Media | Completar JWT: agregar claims `iss`, `aud`, `nbf`, `jti` y rol; a├▒adir **refresh token + blacklist** y **rate limit** en login; cookie `Secure=true` + `SameSite=Strict`. |
| B5 | ­ƒƒá Media | A├▒adir **Dockerfile** y servicios `backend` y `nginx` al Compose; sacar `application.properties` del gitignore o proveer `.env.example` funcional. |
| B6 | ­ƒƒá Media | Subir cobertura de pruebas: m├¡nimo 10 tests de feature/integraci├│n reales (los que ya cita la matriz) y reporte JaCoCo con umbral. |
| B7 | ­ƒƒí Baja | Alinear la documentaci├│n: Angular 21.1 (no 17), quitar endpoints inexistentes de Postman/fuera de la matriz, e invocar realmente los SPs desde repositorios o declararlos pendientes. |

## 6. Veredicto final

El proyecto PRESUS tiene **calidad documental y de modelado de dominio sobresalientes** (lo mejor de la evaluaci├│n),
una base de seguridad razonable y un flujo funcional completo. Sin embargo, **no cumple el criterio espec├¡fico m├ís
importante de la Pr├íctica Experimental de la Unidad IV: el consumo de una API REST externa con cach├® y gesti├│n de
errores (Paso 3, OE3)** ÔÇö que vale 20 de 100 puntos ÔÇö ni alcanza el m├¡nimo de pruebas de la gu├¡a.

**Calificaci├│n final: 62,0 / 100 ÔåÆ Suficiente (con las brechas cr├¡ticas B1ÔÇôB3 por cerrar antes de la defensa).**
Con la correcci├│n de B1ÔÇôB3 y B5 (Docker completo), el proyecto alcanza f├ícilmente el rango de 80ÔÇô90, dado que su
documentaci├│n y dominio ya son consistentes.
