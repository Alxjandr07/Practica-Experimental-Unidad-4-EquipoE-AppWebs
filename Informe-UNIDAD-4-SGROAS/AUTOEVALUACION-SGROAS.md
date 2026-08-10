# Autoevaluación — PFC SGROAS (Evaluación cruzada · Equipo E)

> **Actividad:** Práctica Experimental Unidad IV — Aplicaciones Web (UTEQ, 5to Nivel A).
> **Proyecto evaluado:** SGROAS — Sistema de Gestión de Recursos Operativos, Administrativos y de Seguridad.
> **Evaluadores:** Tejada Bajaña Luis Alejandro · Alava Alvarado Jean Pierre.
> **Propósito:** análisis crítico de qué falta y qué debería mejorar en el proyecto, para guiar la
> retroalimentación entre los integrantes de la actividad combinada PFC + práctica experimental.

---

## 1. Resumen ejecutivo

SGROAS es un proyecto sólido: Spring Boot 3.5 + Java 21, JWT stateless con refresh token en Redis, OpenAPI 2.8.6,
150 pruebas JUnit 5 (cobertura de líneas 99,7 %), prueba de carga k6 con p95 de 81,43 ms y 0 % de errores, y Docker
Compose reproducible. Sin embargo, el análisis contra los criterios exigidos por la guía de la Unidad IV identifica
**brechas concretas** que deben cerrarse para cumplir plenamente el resultado de aprendizaje: la **integración de una
API REST externa**, la **versión de la API**, el **proxy de borde (nginx)** y la **documentación actualizada del
frontend**.

La siguiente tabla clasifica los hallazgos por severidad y prioridad de atención.

## 2. Hallazgos: qué falta y qué mejorar

| # | Área | Hallazgo | Estado actual | Impacto en la guía PE-U4 | Prioridad |
|---|------|----------|---------------|--------------------------|-----------|
| E1 | API externa | No existe consumo de ninguna API REST externa (JSONPlaceholder, clima, rutas, etc.) en `src/main`. No hay `WebClient`/`RestClient`/`RestTemplate` ni reintentos con backoff. | **Falta** | Incumple el "Paso 3 — Consumo de API externa con gestión de errores y caché" (OE3). | 🔴 Alta |
| E2 | Caché externa | El caché está bien implementado para listados propios (`@Cacheable`, TTL `app.cache.default-ttl=300`), pero no hay clave `*api_externa*` en Redis porque no hay API externa. | **Falta** | Criterio de verificación: `redis-cli KEYS "*api_externa*"` falla. | 🔴 Alta |
| E3 | API REST | La ruta base es `/api` sin versión (`/api/v1/`). La guía pide versionado explícito de la API. La estructura de respuesta no usa el formato `{success, data, message, errors, meta}`. | **Parcial** | Incumple parcialmente OE2 (respuestas JSON estructuradas y versionado). | 🔴 Alta |
| E4 | Frontend | README declara "Angular 17+" pero `package.json` usa **Angular 20.3**. Páginas `seguridad`, `administracion` y `reportes` son placeholders sin API. | **Desactualizado** | Documentación inconsistente; la defensa en vivo exponería la discrepancia. | 🟠 Media |
| E5 | Infraestructura | Docker Compose tiene `postgres`, `redis` y `backend`, pero **no hay nginx** ni balanceador. La guía pide la pila `(app, mysql, redis, nginx)`. | **Falta (nginx)** | Criterio "Docker Compose de producción" se cumple a medias. | 🟠 Media |
| E6 | Seguridad | Muy buena base (OWASP A01–A07 + XSS, rate limit 6/60 s, headers CSP/HSTS, BCrypt). Faltan: **MFA**, `SecurityHeaders` completo, prueba explícita de **CSRF** y documentación de A04 (diseño inseguro). | **Parcial** | La guía pide auditoría A01–A07 y XSS con tabla de evidencia; A04 no está cubierto. | 🟠 Media |
| E7 | Métricas | k6 usa 50 VUs/30 s. La guía pregunta por escalado a **500 usuarios concurrentes** y por **Apache Bench** (`ab`). La evidencia de `ab` no está en el repo (solo k6). | **Parcial** | Pregunta Anexo C.2 y criterio 4.2 dependen de `ab`. | 🟠 Media |
| E8 | Código-On-Demand | No aplicado (es opcional en REST). Se documenta como "no aplicado" — correcto que se declare. | N/A | Sin impacto. | ⚪ Informativo |
| E9 | Pruebas | 150 pruebas excellentes (99,7 % líneas). No hay prueba de integración que consuma la API externa simulada (WireMock) ni test de caché TTL. | **Mejora** | Fortalecería la evidencia del Paso 3. | 🟡 Baja |
| E10 | Monitoreo | Hay logging del filtro JWT (A09), pero no hay health checks expuestos (`/actuator/health`) ni métricas Prometheus. | **Mejora** | Útil para el despliegue y la defensa. | 🟡 Baja |

## 3. Plan de mejora propuesto

| Hallazgo | Acción propuesta | Evidencia esperada |
|----------|------------------|--------------------|
| E1 | Implementar `WebClient` (o `RestClient`) con *connect/read timeout*, `onErrorResume` para 4xx/5xx y `retryWhen` con backoff exponencial; consumir una API apropiada al dominio (p. ej. rutas/geocodificación o clima rural). | Fragmento de código + captura en la UI + mensaje amigable ante error de red. |
| E2 | Anotar el servicio de la API externa con `@Cacheable("api_externa")` y TTL por dominio (10 min clima, 1 h divisas, 24 h datos estáticos). | `redis-cli KEYS "*api_externa*"` muestra la clave con TTL. |
| E3 | Mover la ruta base a `/api/v1/` (o declarar versión por cabecera `Accept-Version`); envolver respuestas en el formato `{success, data, message, errors, meta}` con un `ResponseWrapper`. | Swagger UI muestra `/api/v1/*` y respuestas estructuradas. |
| E4 | Actualizar badges/README a Angular 20.3; completar o quitar los placeholders, o documentarlos como pendientes. | README coherente; páginas funcionales o pendientes declaradas. |
| E5 | Añadir servicio `nginx` al Compose, con `depends_on: backend`, proxy a `http://backend:8080`, serving del frontend build y terminación TLS. | `docker compose ps` con `nginx`, `backend`, `redis`, `postgres` en `Up`. |
| E6 | Agregar MFA opcional, revisar con https://securityheaders.com, documentar prueba CSRF y auditoría A04. | Evidencias A04/A08 en `docs/mediciones/sec/`. |
| E7 | Documentar el comando `ab` (`ab -n 1000 -c 100 http://localhost:8080/api/v1/conductores`) y guardar su resultado; ejecutar k6 con `vus=500` para el escenario del anexo C.2. | Archivo de salida de `ab` + JSON de k6 vus=500 en `docs/mediciones/perf/`. |
| E9 | Añadir tests de integración con WireMock para el cliente externo y tests de caché (hit grande, miss luego de TTL). | `./mvnw test` con nuevos casos en verde. |
| E10 | Exponer `/actuator/health` (y opcional `/actuator/prometheus`) y conectarlos a un healthcheck en Compose. | `curl localhost:8080/actuator/health` devuelve `UP`. |

## 4. Puntos fuertes a conservar

- **Seguridad defendible:** JWT stateless con 7 claims RFC 7519, cookie `HttpOnly+Secure+SameSite=Strict`, rate limit
  de 6 intentos/60 s → 429, BCrypt y headers CSP/HSTS verificados con evidencia.
- **Calidad de pruebas:** 150 pruebas JUnit 5, cobertura de líneas 99,7 % y ramas 85,4 % (JaCoCo), umbral ≥ 60 %.
- **Rendimiento medido:** p95 de 81,43 ms y throughput de 49,64 req/s con 0 % de error (k6, 50 VUs).
- **Decisiones documentadas:** 6 ADR (Nygard) y matrices de trazabilidad error→criterio→evidencia.
- **Despliegue reproducible:** Dockerfile multi-etapa + Compose con `postgres:18`, `redis:7` y backend.

## 5. Conclusión de la autoevaluación

El proyecto cumple con holgura los criterios de arquitectura MVC, seguridad, pruebas y rendimiento exigidos en las
prácticas anteriores y en gran parte de la Unidad IV. La brecha principal —por ser el criterio específico del Paso 3—
es la **ausencia de consumo de una API REST externa con caché y gestión de errores**, junto con el versionado de la
API y el proxy `nginx` del despliegue de producción. Con las acciones del plan de mejora y la retroalimentación de
los integrantes del Equipo E, SGROAS alcanza la totalidad de los criterios de verificación de la Práctica
Experimental de la Unidad IV.