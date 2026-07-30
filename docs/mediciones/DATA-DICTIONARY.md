# Diccionario de Datos — Mediciones

## Rendimiento (k6)

| Variable | Tipo | Unidad | Rango esperado | Significado |
|---|---|---|---|---|
| `http_req_duration` | float | ms | 10–500 | Tiempo de respuesta por peticion |
| `http_req_failed` | float | % | 0–1 | Porcentaje de peticiones fallidas |
| `http_reqs` | int | count | — | Total de peticiones realizadas |
| `iterations` | int | count | — | Iteraciones completadas |
| `vus` | int | count | 0–50 | Usuarios virtuales concurrentes |
| `data_received` | int | bytes | — | Datos recibidos |
| `data_sent` | int | bytes | — | Datos enviados |
| `p50` | float | ms | 10–200 | Percentil 50 del tiempo de respuesta |
| `p90` | float | ms | 10–350 | Percentil 90 del tiempo de respuesta |
| `p95` | float | ms | 10–500 | Percentil 95 del tiempo de respuesta |
| `p99` | float | ms | 10–800 | Percentil 99 del tiempo de respuesta |
| `throughput` | float | req/s | 10–500 | Peticiones por segundo |

## Seguridad (OWASP)

| Variable | Tipo | Unidad | Rango esperado | Significado |
|---|---|---|---|---|
| `http_status` | int | HTTP code | 200–599 | Codigo de respuesta HTTP |
| `response_time` | float | ms | 10–5000 | Tiempo de respuesta del endpoint |
| `tls_version` | string | — | TLSv1.2/TLSv1.3 | Version de TLS negociada |
| `cipher_suite` | string | — | — | Suite de cifrado negociada |

## Usabilidad (SUS)

| Variable | Tipo | Unidad | Rango esperado | Significado |
|---|---|---|---|---|
| `participante` | string | — | P01–P10 | Codigo anonimizado del participante |
| `item_01` | int | Likert | 1–5 | Respuesta a pregunta 1 SUS |
| `item_02` | int | Likert | 1–5 | Respuesta a pregunta 2 SUS |
| `item_03` | int | Likert | 1–5 | Respuesta a pregunta 3 SUS |
| `item_04` | int | Likert | 1–5 | Respuesta a pregunta 4 SUS |
| `item_05` | int | Likert | 1–5 | Respuesta a pregunta 5 SUS |
| `item_06` | int | Likert | 1–5 | Respuesta a pregunta 6 SUS |
| `item_07` | int | Likert | 1–5 | Respuesta a pregunta 7 SUS |
| `item_08` | int | Likert | 1–5 | Respuesta a pregunta 8 SUS |
| `item_09` | int | Likert | 1–5 | Respuesta a pregunta 9 SUS |
| `item_10` | int | Likert | 1–5 | Respuesta a pregunta 10 SUS |
| `score` | float | 0–100 | 0–100 | Puntuacion SUS calculada |

## Cobertura (JaCoCo)

| Variable | Tipo | Unidad | Rango esperado | Significado |
|---|---|---|---|---|
| `line_coverage` | float | % | 0–100 | Porcentaje de lineas cubiertas |
| `branch_coverage` | float | % | 0–100 | Porcentaje de ramas cubiertas |
| `complexity_coverage` | float | % | 0–100 | Complejidad ciclomatica cubierta |
| `method_coverage` | float | % | 0–100 | Porcentaje de metodos cubiertos |
| `class_coverage` | float | % | 0–100 | Porcentaje de clases cubiertas |

## Accesibilidad (Lighthouse)

| Variable | Tipo | Unidad | Rango esperado | Significado |
|---|---|---|---|---|
| `performance` | float | score | 0–100 | Puntaje de rendimiento |
| `accessibility` | float | score | 0–100 | Puntaje de accesibilidad |
| `best_practices` | float | score | 0–100 | Puntaje de buenas practicas |
| `seo` | float | score | 0–100 | Puntaje de SEO |
| `first_contentful_paint` | float | ms | 500–5000 | Primera pintura de contenido |
| `largest_contentful_paint` | float | ms | 1000–8000 | Mayor pintura de contenido |
| `cumulative_layout_shift` | float | score | 0–1 | Cambio de layout acumulado |
| `total_blocking_time` | float | ms | 0–1000 | Tiempo de bloqueo total |
