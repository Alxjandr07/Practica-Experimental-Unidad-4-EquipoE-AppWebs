# Autoevaluación Individual — PFC SGROAS (Evaluación cruzada · Equipo E)

> **Actividad:** Práctica Experimental Unidad IV — Aplicaciones Web (UTEQ, 5to Nivel A).
> **Proyecto evaluado:** SGROAS — Sistema de Gestión de Recursos Operativos, Administrativos y de Seguridad.
> **Evaluador:** Alava Alvarado Jean Pierre.
> **Fecha:** Agosto de 2026.
> **Propósito:** revisión individual del estado del proyecto, identificación de fortalezas, debilidades y
> oportunidades de mejora desde la óptica del frontend, la experiencia de usuario y la completitud funcional
> frente a los criterios de la guía de Práctica Experimental de la Unidad IV.

---

## 1. Resumen ejecutivo

SGROAS demuestra una base técnica robusta en su capa backend: Spring Boot 3.5 sobre Java 21 con seguridad JWT
reforzada por Redis, migraciones Flyway versionadas, cobertura de pruebas superior al 99 % y un rendimiento
comprobado con k6 (p95 ≤ 82 ms). No obstante, al evaluar el proyecto de forma integral —backend **y**
frontend— se identifican **brechas funcionales relevantes** que afectan la demostración del sistema ante los
criterios de la Unidad IV. Los cuatro módulos de interfaz de usuario pendientes (Rutas, Seguridad,
Administración y Reportes), la ausencia de consumo de API externa, la falta de versionado explícito del API y
la carencia de un proxy inverso en la infraestructura constituyen los principales puntos de atención.

Esta autoevaluación se centra en tres ejes: **completitud funcional del frontend**, **alineación con los
criterios de la guía PE-U4** y **experiencia de usuario como factor de calidad**.

## 2. Diagnóstico por área

### 2.1 Estado funcional del frontend

| Módulo | Componentes implementados | Conectado al backend | Observación |
|--------|--------------------------|---------------------|-------------|
| Login | ✅ Formulario reactivo + validación | ✅ `POST /api/auth/login` | Funcional; maneja 401, loading, redirección. |
| Dashboard / Overview | ✅ Tarjetas + grid de módulos | ❌ Datos hardcodeados | Las estadísticas (42 unidades, 18 rutas…) son valores fijos; no consultan la API. |
| Usuarios y Roles | ✅ Lista + formulario CRUD | ✅ `GET/POST/PUT/DELETE /api/usuarios` | Funcional con paginación. |
| Flota Vehicular (Conductores) | ✅ Lista + formulario CRUD | ✅ `GET/POST/PUT/DELETE /api/conductores` | Funcional; falta CRUD de vehículos en la misma vista. |
| Rutas y Frecuencias | ⚠️ Placeholder inline | ❌ | El backend tiene `RutaController` y `AsignacionRutaController` listos, pero no se consumen. |
| Seguridad / Incidentes | ⚠️ Placeholder inline | ❌ | `IncidenteController` existe en el backend pero sin vista frontal. |
| Administración | ⚠️ Placeholder inline | ❌ | Sin funcionalidad definida ni endpoint específico. |
| Reportes | ⚠️ Placeholder inline | ❌ | Los 7 stored procedures (`fn_estadisticas_generales`, `sp_reporte_rendimiento_rutas`, etc.) no se visualizan. |

**Diagnóstico:** De 8 módulos declarados, solo 3 están plenamente funcionales (Login, Usuarios, Conductores).
El Overview está operativo pero con datos estáticos. Los 4 restantes son componentes vacíos con template inline
que muestran únicamente texto placeholder.

### 2.2 Consumo de API externa

| Criterio de la guía PE-U4 | Estado | Evidencia |
|---------------------------|--------|-----------|
| Consumo de al menos una API REST externa | ❌ No implementado | No existe `WebClient`, `RestClient` ni `RestTemplate` en `src/main`. |
| Gestión de errores (4xx/5xx) con respuesta amigable | ❌ No implementado | Sin código de fallback ni `onErrorResume`. |
| Reintentos con backoff exponencial | ❌ No implementado | Sin `retryWhen` ni configuración de reintentos. |
| Caché de respuestas externas en Redis | ❌ No implementado | `redis-cli KEYS "*api_externa*"` no devuelve claves. |

**Diagnóstico:** Esta es la brecha más crítica para el cumplimiento del Paso 3 (OE3) de la guía. El caché
Redis funciona correctamente para los listados propios, pero la ausencia total de integración externa impide
demostrar interoperabilidad con servicios de terceros.

### 2.3 Versionado y estructura de respuestas de la API

| Aspecto | Esperado por la guía | Estado actual |
|---------|---------------------|---------------|
| Prefijo versionado | `/api/v1/conductores` | `/api/conductores` (sin versión) |
| Formato de respuesta unificado | `{success, data, message, errors, meta}` | Respuesta directa del DTO sin envoltura estándar |
| Paginación en metadatos | Campo `meta` con `page`, `size`, `totalElements` | Se devuelve paginación pero sin formato estandarizado |
| Documentación OpenAPI | Swagger disponible en `/api/docs` | ✅ Funcional con SpringDoc 2.8.6 |

### 2.4 Infraestructura y despliegue

| Componente | Requerido | Implementado |
|-----------|-----------|-------------|
| PostgreSQL | ✅ | ✅ Postgres 18, SHA256 pinned |
| Redis | ✅ | ✅ Redis 7, SHA256 pinned |
| Backend (Spring Boot) | ✅ | ✅ Dockerfile multi-etapa |
| Nginx / proxy inverso | ✅ | ❌ No existe servicio nginx en `docker-compose.yml` |
| Frontend servido | ✅ | ⚠️ Existe `serve-gzip.js` pero no se integra en el Compose |
| TLS / HTTPS | Recomendado | ❌ No configurado |

### 2.5 Seguridad

| Control | Estado | Detalle |
|---------|--------|---------|
| JWT Stateless | ✅ | Access token 1h + refresh 7d, blacklist en Redis |
| Cookies HttpOnly + Secure + SameSite | ✅ | Implementado en `AuthController` |
| BCrypt | ✅ | `BCryptPasswordEncoder` en `SecurityConfig` |
| Rate limiting login | ✅ | `LoginRateLimiter` bloquea IP tras 6 intentos → 429 |
| Headers HSTS | ✅ | `maxAge=31536000`, `includeSubDomains=true` |
| Content-Security-Policy | ✅ | `default-src 'self'; script-src 'self'` |
| X-Frame-Options | ✅ | `DENY` |
| CORS configurado | ✅ | Solo `localhost:4200` permitido |
| MFA (autenticación multifactor) | ❌ | No implementado |
| Auditoría OWASP A04 (diseño inseguro) | ❌ | Sin documentación explícita |
| Prueba CSRF documentada | ❌ | CSRF está deshabilitado (stateless), pero falta justificación documentada |

### 2.6 Experiencia de usuario (UX)

| Aspecto | Evaluación | Observación |
|---------|-----------|-------------|
| Diseño del login | ✅ Bueno | Layout dividido, branding visual, validación inline, estados de carga. |
| Navbar del shell | ✅ Aceptable | Links claros, avatar con iniciales, logout funcional. |
| Responsividad | ⚠️ Parcial | No hay media queries en el shell ni en las tablas de CRUD; en móvil se desborda. |
| Feedback visual de acciones | ⚠️ Limitado | No hay toasts/snackbars de éxito al crear/editar; solo se muestran errores. |
| Accesibilidad (a11y) | ⚠️ Básica | Inputs con labels, pero sin `aria-live`, roles ARIA ni skip-navigation. |
| `app.html` | ⚠️ Problema | Conserva el template placeholder de Angular por defecto (logo Angular, pills de links). Debería solo tener `<router-outlet>`. |

## 3. Hallazgos consolidados (diferenciados por criticidad)

| # | Hallazgo | Categoría | Impacto | Prioridad |
|---|----------|-----------|---------|-----------|
| H1 | No hay consumo de API REST externa (OE3 de la guía) | Funcional | Incumple Paso 3 — criterio obligatorio | 🔴 Crítica |
| H2 | 4 módulos frontend son placeholder sin funcionalidad real | Completitud | La defensa en vivo expone módulos vacíos | 🔴 Crítica |
| H3 | API sin versionado (`/api/` en vez de `/api/v1/`) | Diseño REST | Incumple parcialmente OE2 | 🔴 Alta |
| H4 | No hay wrapper de respuesta unificado (`{success, data, meta}`) | Diseño REST | Respuestas inconsistentes para el consumidor | 🟠 Media |
| H5 | Falta nginx en Docker Compose | Infraestructura | Despliegue incompleto según la guía | 🟠 Media |
| H6 | Overview con datos hardcodeados (no conecta a la API) | Frontend | No refleja el estado real de la BD | 🟠 Media |
| H7 | `app.html` conserva el placeholder de Angular (logo, pills) | Frontend | Se renderiza detrás del router-outlet; se ve en defensa | 🟠 Media |
| H8 | Sin feedback visual positivo (toasts de éxito) en CRUD | UX | Experiencia incompleta tras operaciones exitosas | 🟡 Baja |
| H9 | Sin responsividad en tablas y shell | UX | Desbordamiento en pantallas < 768px | 🟡 Baja |
| H10 | Sin health checks expuestos (`/actuator/health`) | Operaciones | No se puede verificar estado en producción | 🟡 Baja |

## 4. Plan de mejora recomendado

### 🔴 Prioridad Crítica / Alta

| Hallazgo | Acción recomendada | Resultado esperado |
|----------|-------------------|--------------------|
| H1 | Implementar un servicio con `RestClient` que consuma una API geográfica o de clima relevante al dominio de transporte (p. ej. OpenWeatherMap para condiciones de ruta, o una API de geocodificación). Incluir timeout, `onErrorResume` y `retryWhen(backoff)`. | Endpoint funcional + datos visibles en el frontend + caché en Redis con TTL configurable. |
| H2 | Desarrollar los componentes Angular pendientes: (a) `VehiculoLista` + `VehiculoFormulario`, (b) `RutaLista` + `RutaFormulario` + `AsignacionLista`, (c) `IncidenteLista` + `IncidenteFormulario`, (d) vista de reportes con llamadas a los stored procedures. | Los 7 módulos del navbar son funcionales con datos reales del backend. |
| H3 | Cambiar el `@RequestMapping` base de todos los controllers a `/api/v1/` y actualizar los servicios Angular. | Swagger muestra `/api/v1/*`; URLs coherentes. |

### 🟠 Prioridad Media

| Hallazgo | Acción recomendada | Resultado esperado |
|----------|-------------------|--------------------|
| H4 | Crear una clase `ApiResponse<T>` genérica que envuelva todas las respuestas en `{success, data, message, errors, meta}`. Aplicar con `@ControllerAdvice` o manualmente. | Todas las respuestas tienen formato consistente. |
| H5 | Agregar servicio `nginx` al `docker-compose.yml` con proxy_pass al backend y serving del build de Angular. | `docker compose ps` muestra 4 servicios: nginx, backend, postgres, redis. |
| H6 | Crear un endpoint `/api/v1/estadisticas/resumen` que invoque `fn_estadisticas_generales()` y conectar el `Overview` del frontend a ese endpoint. | Dashboard muestra datos reales en las tarjetas de resumen. |
| H7 | Limpiar `app.html`: eliminar todo el contenido placeholder y dejar solo `<router-outlet />`. | Sin artefactos visuales del scaffolding de Angular. |

### 🟡 Prioridad Baja

| Hallazgo | Acción recomendada | Resultado esperado |
|----------|-------------------|--------------------|
| H8 | Implementar un servicio de notificaciones con `MatSnackBar` o un componente toast personalizado. | Mensajes de éxito verdes tras crear/editar/eliminar. |
| H9 | Agregar media queries al shell y tablas para pantallas < 768px; usar `overflow-x: auto` en las tablas. | Navegable en dispositivos móviles sin desbordamiento. |
| H10 | Agregar dependencia `spring-boot-starter-actuator`, exponer `/actuator/health` y configurar healthcheck en Docker Compose. | `curl localhost:8080/actuator/health` retorna `{"status":"UP"}`. |

## 5. Fortalezas a preservar

1. **Modelo de datos bien normalizado:** 6 entidades con constraints CHECK, triggers de actualización automática,
   índices únicos y FKs declaradas. Las migraciones Flyway garantizan reproducibilidad del schema.

2. **Arquitectura backend limpia:** Separación estricta en capas (controller → service → repository → entity) con
   DTOs de request/response que evitan exponer entidades JPA directamente.

3. **Seguridad multicapa:** JWT con 7 claims RFC 7519, cookies HttpOnly+Secure+SameSite, rate limiting por IP,
   BCrypt, headers HSTS/CSP/X-Frame-Options. Es una de las implementaciones más completas entre los proyectos
   revisados.

4. **Calidad de pruebas excepcional:** 150 test cases con 99,7 % de cobertura de líneas y 85,4 % de ramas. El
   umbral JaCoCo de 60 % se supera ampliamente.

5. **Stored procedures útiles:** 7 funciones/procedimientos almacenados que demuestran lógica de negocio en la
   base de datos (estadísticas, licencias por vencer, rendimiento de rutas, incidentes por gravedad).

6. **Documentación estructurada:** ADRs con formato Nygard, matrices de trazabilidad, informe LaTeX con secciones
   alineadas a la guía, colección Postman documentada.

## 6. Conclusión

Desde mi perspectiva como evaluador, SGROAS es un proyecto que destaca en su capa de backend y seguridad — la
calidad del código Java, la cobertura de pruebas y la implementación de JWT con Redis son de un nivel superior al
esperado para el curso. Sin embargo, la desconexión entre el backend completo y un frontend parcialmente
implementado genera una brecha visible durante la demostración: 4 de los 7 módulos principales del sistema no
tienen interfaz funcional, el dashboard muestra datos ficticios y el template Angular por defecto aún es visible.

La prioridad absoluta debería ser **completar la integración frontend-backend** en los módulos pendientes y
**consumir al menos una API externa con caché en Redis**, ya que son los criterios diferenciadores de la Unidad IV.
Con estas mejoras y la retroalimentación del Equipo E, SGROAS estaría en posición de cumplir la totalidad de los
criterios de verificación de la Práctica Experimental.

---

*Documento elaborado por Alava Alvarado Jean Pierre como parte de la evaluación cruzada del Equipo E.*
