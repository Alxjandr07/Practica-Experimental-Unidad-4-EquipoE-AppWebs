workspace "SGROAS - Sistema de Gestion de Rutas y Asignaciones Operativas" "Sistema de gestion de flota de transporte" {

    model {
        u = person "Usuario" "Operador del sistema (admin, coordinador, seguridad)"
        s = softwareSystem "SGROAS API" "Plataforma de gestion de rutas, conductores, vehiculos e incidentes"

        u -> s "Utiliza la plataforma via" "API REST / Frontend Vue.js"
    }

    views {
        systemContext s "Nivel 1 - Diagrama de Contexto" {
            include *
            autoLayout
        }
    }
}
