# Reporte de cobertura de código (JaCoCo)

## Resumen

| Métrica | Cubierto | No cubierto | Total | Cobertura |
|---|---|---|---|---|
| Instrucciones | 3484 | 43 | 3527 | 98,8 % |
| Ramas | 70 | 12 | 82 | 85,4 % |
| Líneas | 778 | 2 | 780 | 99,7 % |

## Umbral exigido (criterio C4, guía Tercera Entrega)

- Mínimo exigido: **60 %**
- Configuración en `pom.xml` (plugin `jacoco-maven-plugin` 0.8.14): regla `BUNDLE` con
  `LINE COVEREDRATIO >= 0.60` y `BRANCH COVEREDRATIO >= 0.60`.
- Resultado de la verificación: **cumple** (`All coverage checks have been met`, BUILD SUCCESS).

## Desglose por paquete (instrucciones/ramas)

| Paquete | Instrucciones | Ramas |
|---|---|---|
| `ec.edu.uteq.sgroas.entity` | 100,0 % | — |
| `ec.edu.uteq.sgroas.dto` | 100,0 % | — |
| `ec.edu.uteq.sgroas.config` | 100,0 % | — |
| `ec.edu.uteq.sgroas.exception` | 100,0 % | — |
| `ec.edu.uteq.sgroas.controller` | 100,0 % | 100,0 % |
| `ec.edu.uteq.sgroas.service` | 98,6 % | 85,7 % |
| `ec.edu.uteq.sgroas.security` | 96,6 % | 83,3 % |
| `ec.edu.uteq.sgroas` (SgroasApplication) | 37,5 % | — |

## Contexto de la medición

- Fecha: 30 de julio de 2026
- Entorno: JDK 25 (Eclipse Adoptium), Maven wrapper (`mvnw`), JaCoCo 0.8.14
- Comando: `./mvnw verify` (150 pruebas JUnit 5, 0 fallos, 0 errores)
- Cobertura previa con JaCoCo 0.8.12: incompleta (omitía clases por "Unsupported class file
  major version 69" con JDK 25); resuelto al actualizar a 0.8.14
- Artefactos generados en este directorio: `index.html`, `jacoco.csv`, `jacoco.xml`,
  `jacoco-sessions.html` y el desglose por paquete
