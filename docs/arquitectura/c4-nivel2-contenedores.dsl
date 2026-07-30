workspace "SGROAS - Nivel 2 - Contenedores" {

    model {
        u = person "Usuario" "Operador del sistema"

        frontend = container "Frontend Vue.js" "Aplicacion SPA" "Vue.js 3"
        api = container "API REST Spring Boot" "Backend Java" "Spring Boot 3.5 + Java 21"
        db = container "Base de Datos PostgreSQL" "Almacenamiento principal" "PostgreSQL 16"
        cache = container "Redis" "Cache distribuido" "Redis 7"
        jwtService = container "JWT Service" "Autenticacion y autorizacion" "jjwt 0.12.6"

        u -> frontend "Navega"
        frontend -> api "Peticiones HTTP" "JSON"
        api -> db "Lectura/Escritura" "SQL"
        api -> cache "Cache de consultas" "Redis Protocol"
        api -> jwtService "Validacion de tokens"
    }

    views {
        container api "Nivel 2 - Diagrama de Contenedores" {
            include *
            autoLayout
        }
    }
}
