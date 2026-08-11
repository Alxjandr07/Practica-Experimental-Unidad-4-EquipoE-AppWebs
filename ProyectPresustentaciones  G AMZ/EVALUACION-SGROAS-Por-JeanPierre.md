# Autoevaluaci├│n Individual ÔÇö PFC SGROAS (Evaluaci├│n cruzada ┬À Equipo E)

> **Actividad:** Pr├íctica Experimental Unidad IV ÔÇö Aplicaciones Web (UTEQ, 5to Nivel A).
> **Proyecto evaluado:** SGROAS ÔÇö Sistema de Gesti├│n de Recursos Operativos, Administrativos y de Seguridad.
> **Evaluador:** Alava Alvarado Jean Pierre.
> **Fecha:** Agosto de 2026.
> **Prop├│sito:** revisi├│n individual del estado del proyecto, identificaci├│n de fortalezas, debilidades y
> oportunidades de mejora desde la ├│ptica del frontend, la experiencia de usuario y la completitud funcional
> frente a los criterios de la gu├¡a de Pr├íctica Experimental de la Unidad IV.

---

## 1. Resumen ejecutivo

SGROAS demuestra una base t├®cnica robusta en su capa backend: Spring Boot 3.5 sobre Java 21 con seguridad JWT
reforzada por Redis, migraciones Flyway versionadas, cobertura de pruebas superior al 99 % y un rendimiento
comprobado con k6 (p95 Ôëñ 82 ms). No obstante, al evaluar el proyecto de forma integral ÔÇöbackend **y**
frontendÔÇö se identifican **brechas funcionales relevantes** que afectan la demostraci├│n del sistema ante los
criterios de la Unidad IV. Los cuatro m├│dulos de interfaz de usuario pendientes (Rutas, Seguridad,
Administraci├│n y Reportes), la ausencia de consumo de API externa, la falta de versionado expl├¡cito del API y
la carencia de un proxy inverso en la infraestructura constituyen los principales puntos de atenci├│n.

Esta autoevaluaci├│n se centra en tres ejes: **completitud funcional del frontend**, **alineaci├│n con los
criterios de la gu├¡a PE-U4** y **experiencia de usuario como factor de calidad**.

## 2. Diagn├│stico por ├írea

### 2.1 Estado funcional del frontend

| M├│dulo | Componentes implementados | Conectado al backend | Observaci├│n |
|--------|--------------------------|---------------------|-------------|
| Login | Ô£à Formulario reactivo + validaci├│n | Ô£à `POST /api/auth/login` | Funcional; maneja 401, loading, redirecci├│n. |
| Dashboard / Overview | Ô£à Tarjetas + grid de m├│dulos | ÔØî Datos hardcodeados | Las estad├¡sticas (42 unidades, 18 rutasÔÇª) son valores fijos; no consultan la API. |
| Usuarios y Roles | Ô£à Lista + formulario CRUD | Ô£à `GET/POST/PUT/DELETE /api/usuarios` | Funcional con paginaci├│n. |
| Flota Vehicular (Conductores) | Ô£à Lista + formulario CRUD | Ô£à `GET/POST/PUT/DELETE /api/conductores` | Funcional; falta CRUD de veh├¡culos en la misma vista. |
| Rutas y Frecuencias | ÔÜá´©Å Placeholder inline | ÔØî | El backend tiene `RutaController` y `AsignacionRutaController` listos, pero no se consumen. |
| Seguridad / Incidentes | ÔÜá´©Å Placeholder inline | ÔØî | `IncidenteController` existe en el backend pero sin vista frontal. |
| Administraci├│n | ÔÜá´©Å Placeholder inline | ÔØî | Sin funcionalidad definida ni endpoint espec├¡fico. |
| Reportes | ÔÜá´©Å Placeholder inline | ÔØî | Los 7 stored procedures (`fn_estadisticas_generales`, `sp_reporte_rendimiento_rutas`, etc.) no se visualizan. |

**Diagn├│stico:** De 8 m├│dulos declarados, solo 3 est├ín plenamente funcionales (Login, Usuarios, Conductores).
El Overview est├í operativo pero con datos est├íticos. Los 4 restantes son componentes vac├¡os con template inline
que muestran ├║nicamente texto placeholder.

### 2.2 Consumo de API externa

| Criterio de la gu├¡a PE-U4 | Estado | Evidencia |
|---------------------------|--------|-----------|
| Consumo de al menos una API REST externa | ÔØî No implementado | No existe `WebClient`, `RestClient` ni `RestTemplate` en `src/main`. |
| Gesti├│n de errores (4xx/5xx) con respuesta amigable | ÔØî No implementado | Sin c├│digo de fallback ni `onErrorResume`. |
| Reintentos con backoff exponencial | ÔØî No implementado | Sin `retryWhen` ni configuraci├│n de reintentos. |
| Cach├® de respuestas externas en Redis | ÔØî No implementado | `redis-cli KEYS "*api_externa*"` no devuelve claves. |

**Diagn├│stico:** Esta es la brecha m├ís cr├¡tica para el cumplimiento del Paso 3 (OE3) de la gu├¡a. El cach├®
Redis funciona correctamente para los listados propios, pero la ausencia total de integraci├│n externa impide
demostrar interoperabilidad con servicios de terceros.

### 2.3 Versionado y estructura de respuestas de la API

| Aspecto | Esperado por la gu├¡a | Estado actual |
|---------|---------------------|---------------|
| Prefijo versionado | `/api/v1/conductores` | `/api/conductores` (sin versi├│n) |
| Formato de respuesta unificado | `{success, data, message, errors, meta}` | Respuesta directa del DTO sin envoltura est├índar |
| Paginaci├│n en metadatos | Campo `meta` con `page`, `size`, `totalElements` | Se devuelve paginaci├│n pero sin formato estandarizado |
| Documentaci├│n OpenAPI | Swagger disponible en `/api/docs` | Ô£à Funcional con SpringDoc 2.8.6 |

### 2.4 Infraestructura y despliegue

| Componente | Requerido | Implementado |
|-----------|-----------|-------------|
| PostgreSQL | Ô£à | Ô£à Postgres 18, SHA256 pinned |
| Redis | Ô£à | Ô£à Redis 7, SHA256 pinned |
| Backend (Spring Boot) | Ô£à | Ô£à Dockerfile multi-etapa |
| Nginx / proxy inverso | Ô£à | ÔØî No existe servicio nginx en `docker-compose.yml` |
| Frontend servido | Ô£à | ÔÜá´©Å Existe `serve-gzip.js` pero no se integra en el Compose |
| TLS / HTTPS | Recomendado | ÔØî No configurado |

### 2.5 Seguridad

| Control | Estado | Detalle |
|---------|--------|---------|
| JWT Stateless | Ô£à | Access token 1h + refresh 7d, blacklist en Redis |
| Cookies HttpOnly + Secure + SameSite | Ô£à | Implementado en `AuthController` |
| BCrypt | Ô£à | `BCryptPasswordEncoder` en `SecurityConfig` |
| Rate limiting login | Ô£à | `LoginRateLimiter` bloquea IP tras 6 intentos ÔåÆ 429 |
| Headers HSTS | Ô£à | `maxAge=31536000`, `includeSubDomains=true` |
| Content-Security-Policy | Ô£à | `default-src 'self'; script-src 'self'` |
| X-Frame-Options | Ô£à | `DENY` |
| CORS configurado | Ô£à | Solo `localhost:4200` permitido |
| MFA (autenticaci├│n multifactor) | ÔØî | No implementado |
| Auditor├¡a OWASP A04 (dise├▒o inseguro) | ÔØî | Sin documentaci├│n expl├¡cita |
| Prueba CSRF documentada | ÔØî | CSRF est├í deshabilitado (stateless), pero falta justificaci├│n documentada |

### 2.6 Experiencia de usuario (UX)

| Aspecto | Evaluaci├│n | Observaci├│n |
|---------|-----------|-------------|
| Dise├▒o del login | Ô£à Bueno | Layout dividido, branding visual, validaci├│n inline, estados de carga. |
| Navbar del shell | Ô£à Aceptable | Links claros, avatar con iniciales, logout funcional. |
| Responsividad | ÔÜá´©Å Parcial | No hay media queries en el shell ni en las tablas de CRUD; en m├│vil se desborda. |
| Feedback visual de acciones | ÔÜá´©Å Limitado | No hay toasts/snackbars de ├®xito al crear/editar; solo se muestran errores. |
| Accesibilidad (a11y) | ÔÜá´©Å B├ísica | Inputs con labels, pero sin `aria-live`, roles ARIA ni skip-navigation. |
| `app.html` | ÔÜá´©Å Problema | Conserva el template placeholder de Angular por defecto (logo Angular, pills de links). Deber├¡a solo tener `<router-outlet>`. |

## 3. Hallazgos consolidados (diferenciados por criticidad)

| # | Hallazgo | Categor├¡a | Impacto | Prioridad |
|---|----------|-----------|---------|-----------|
| H1 | No hay consumo de API REST externa (OE3 de la gu├¡a) | Funcional | Incumple Paso 3 ÔÇö criterio obligatorio | ­ƒö┤ Cr├¡tica |
| H2 | 4 m├│dulos frontend son placeholder sin funcionalidad real | Completitud | La defensa en vivo expone m├│dulos vac├¡os | ­ƒö┤ Cr├¡tica |
| H3 | API sin versionado (`/api/` en vez de `/api/v1/`) | Dise├▒o REST | Incumple parcialmente OE2 | ­ƒö┤ Alta |
| H4 | No hay wrapper de respuesta unificado (`{success, data, meta}`) | Dise├▒o REST | Respuestas inconsistentes para el consumidor | ­ƒƒá Media |
| H5 | Falta nginx en Docker Compose | Infraestructura | Despliegue incompleto seg├║n la gu├¡a | ­ƒƒá Media |
| H6 | Overview con datos hardcodeados (no conecta a la API) | Frontend | No refleja el estado real de la BD | ­ƒƒá Media |
| H7 | `app.html` conserva el placeholder de Angular (logo, pills) | Frontend | Se renderiza detr├ís del router-outlet; se ve en defensa | ­ƒƒá Media |
| H8 | Sin feedback visual positivo (toasts de ├®xito) en CRUD | UX | Experiencia incompleta tras operaciones exitosas | ­ƒƒí Baja |
| H9 | Sin responsividad en tablas y shell | UX | Desbordamiento en pantallas < 768px | ­ƒƒí Baja |
| H10 | Sin health checks expuestos (`/actuator/health`) | Operaciones | No se puede verificar estado en producci├│n | ­ƒƒí Baja |

## 4. Plan de mejora recomendado

### ­ƒö┤ Prioridad Cr├¡tica / Alta

| Hallazgo | Acci├│n recomendada | Resultado esperado |
|----------|-------------------|--------------------|
| H1 | Implementar un servicio con `RestClient` que consuma una API geogr├ífica o de clima relevante al dominio de transporte (p. ej. OpenWeatherMap para condiciones de ruta, o una API de geocodificaci├│n). Incluir timeout, `onErrorResume` y `retryWhen(backoff)`. | Endpoint funcional + datos visibles en el frontend + cach├® en Redis con TTL configurable. |
| H2 | Desarrollar los componentes Angular pendientes: (a) `VehiculoLista` + `VehiculoFormulario`, (b) `RutaLista` + `RutaFormulario` + `AsignacionLista`, (c) `IncidenteLista` + `IncidenteFormulario`, (d) vista de reportes con llamadas a los stored procedures. | Los 7 m├│dulos del navbar son funcionales con datos reales del backend. |
| H3 | Cambiar el `@RequestMapping` base de todos los controllers a `/api/v1/` y actualizar los servicios Angular. | Swagger muestra `/api/v1/*`; URLs coherentes. |

### ­ƒƒá Prioridad Media

| Hallazgo | Acci├│n recomendada | Resultado esperado |
|----------|-------------------|--------------------|
| H4 | Crear una clase `ApiResponse<T>` gen├®rica que envuelva todas las respuestas en `{success, data, message, errors, meta}`. Aplicar con `@ControllerAdvice` o manualmente. | Todas las respuestas tienen formato consistente. |
| H5 | Agregar servicio `nginx` al `docker-compose.yml` con proxy_pass al backend y serving del build de Angular. | `docker compose ps` muestra 4 servicios: nginx, backend, postgres, redis. |
| H6 | Crear un endpoint `/api/v1/estadisticas/resumen` que invoque `fn_estadisticas_generales()` y conectar el `Overview` del frontend a ese endpoint. | Dashboard muestra datos reales en las tarjetas de resumen. |
| H7 | Limpiar `app.html`: eliminar todo el contenido placeholder y dejar solo `<router-outlet />`. | Sin artefactos visuales del scaffolding de Angular. |

### ­ƒƒí Prioridad Baja

| Hallazgo | Acci├│n recomendada | Resultado esperado |
|----------|-------------------|--------------------|
| H8 | Implementar un servicio de notificaciones con `MatSnackBar` o un componente toast personalizado. | Mensajes de ├®xito verdes tras crear/editar/eliminar. |
| H9 | Agregar media queries al shell y tablas para pantallas < 768px; usar `overflow-x: auto` en las tablas. | Navegable en dispositivos m├│viles sin desbordamiento. |
| H10 | Agregar dependencia `spring-boot-starter-actuator`, exponer `/actuator/health` y configurar healthcheck en Docker Compose. | `curl localhost:8080/actuator/health` retorna `{"status":"UP"}`. |

## 5. Fortalezas a preservar

1. **Modelo de datos bien normalizado:** 6 entidades con constraints CHECK, triggers de actualizaci├│n autom├ítica,
   ├¡ndices ├║nicos y FKs declaradas. Las migraciones Flyway garantizan reproducibilidad del schema.

2. **Arquitectura backend limpia:** Separaci├│n estricta en capas (controller ÔåÆ service ÔåÆ repository ÔåÆ entity) con
   DTOs de request/response que evitan exponer entidades JPA directamente.

3. **Seguridad multicapa:** JWT con 7 claims RFC 7519, cookies HttpOnly+Secure+SameSite, rate limiting por IP,
   BCrypt, headers HSTS/CSP/X-Frame-Options. Es una de las implementaciones m├ís completas entre los proyectos
   revisados.

4. **Calidad de pruebas excepcional:** 150 test cases con 99,7 % de cobertura de l├¡neas y 85,4 % de ramas. El
   umbral JaCoCo de 60 % se supera ampliamente.

5. **Stored procedures ├║tiles:** 7 funciones/procedimientos almacenados que demuestran l├│gica de negocio en la
   base de datos (estad├¡sticas, licencias por vencer, rendimiento de rutas, incidentes por gravedad).

6. **Documentaci├│n estructurada:** ADRs con formato Nygard, matrices de trazabilidad, informe LaTeX con secciones
   alineadas a la gu├¡a, colecci├│n Postman documentada.

## 6. Conclusi├│n

Desde mi perspectiva como evaluador, SGROAS es un proyecto que destaca en su capa de backend y seguridad ÔÇö la
calidad del c├│digo Java, la cobertura de pruebas y la implementaci├│n de JWT con Redis son de un nivel superior al
esperado para el curso. Sin embargo, la desconexi├│n entre el backend completo y un frontend parcialmente
implementado genera una brecha visible durante la demostraci├│n: 4 de los 7 m├│dulos principales del sistema no
tienen interfaz funcional, el dashboard muestra datos ficticios y el template Angular por defecto a├║n es visible.

La prioridad absoluta deber├¡a ser **completar la integraci├│n frontend-backend** en los m├│dulos pendientes y
**consumir al menos una API externa con cach├® en Redis**, ya que son los criterios diferenciadores de la Unidad IV.
Con estas mejoras y la retroalimentaci├│n del Equipo E, SGROAS estar├¡a en posici├│n de cumplir la totalidad de los
criterios de verificaci├│n de la Pr├íctica Experimental.

---

*Documento elaborado por Alava Alvarado Jean Pierre como parte de la evaluaci├│n cruzada del Equipo E.*
