workspace "SGROAS - Nivel 3 - Componentes" {

    model {
        api = container "API REST Spring Boot" "" "Spring Boot 3.5 + Java 21" {
            auth = component "Modulo de Autenticacion" "Login, registro, refresh token" "Spring Security + JWT"
            conductor = component "Modulo Conductores" "CRUD de conductores" "Spring MVC + JPA"
            vehiculo = component "Modulo Vehiculos" "CRUD de vehiculos" "Spring MVC + JPA"
            ruta = component "Modulo Rutas" "CRUD de rutas" "Spring MVC + JPA"
            asignacion = component "Modulo Asignaciones" "CRUD de asignaciones ruta-conductor-vehiculo" "Spring MVC + JPA"
            incidente = component "Modulo Incidentes" "CRUD de incidentes" "Spring MVC + JPA"
            sp = component "Stored Procedures" "Reportes y agregaciones en BD" "PostgreSQL PL/pgSQL"
        }

        db = container "PostgreSQL" "" "PostgreSQL 16" {
            usuarios = component "Tabla usuarios" ""
            conductores = component "Tabla conductores" ""
            vehiculos = component "Tabla vehiculos" ""
            rutas = component "Tabla rutas" ""
            asignaciones = component "Tabla asignacion_rutas" ""
            incidentes = component "Tabla incidentes" ""
        }

        conductor -> db.conductores "CRUD"
        vehiculo -> db.vehiculos "CRUD"
        ruta -> db.rutas "CRUD"
        asignacion -> db.asignaciones "CRUD"
        asignacion -> db.conductores "FK"
        asignacion -> db.vehiculos "FK"
        asignacion -> db.rutas "FK"
        incidente -> db.incidentes "CRUD"
        incidente -> db.asignaciones "FK"
        auth -> db.usuarios "CRUD"
    }

    views {
        component api "Nivel 3 - Diagrama de Componentes" {
            include *
            autoLayout
        }
    }
}
