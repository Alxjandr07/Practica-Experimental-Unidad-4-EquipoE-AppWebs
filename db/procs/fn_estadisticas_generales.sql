-- =============================================================================
-- fn_estadisticas_generales
-- Descripcion: Funcion que retorna estadisticas resumidas de todo el sistema:
--              totales de conductores, vehiculos, rutas, asignaciones e
--              incidentes. Util para dashboards.
-- Uso: Funcion sin parametros, multiples agregaciones
-- =============================================================================

CREATE OR REPLACE FUNCTION fn_estadisticas_generales()
    RETURNS TABLE(
        total_conductores BIGINT,
        conductores_activos BIGINT,
        total_vehiculos BIGINT,
        vehiculos_activos BIGINT,
        total_rutas BIGINT,
        rutas_activas BIGINT,
        total_asignaciones BIGINT,
        asignaciones_activas BIGINT,
        total_incidentes BIGINT,
        incidentes_abiertos BIGINT
    )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT
        (SELECT COUNT(*) FROM conductores)::BIGINT,
        (SELECT COUNT(*) FROM conductores WHERE activo = true)::BIGINT,
        (SELECT COUNT(*) FROM vehiculos)::BIGINT,
        (SELECT COUNT(*) FROM vehiculos WHERE activo = true)::BIGINT,
        (SELECT COUNT(*) FROM rutas)::BIGINT,
        (SELECT COUNT(*) FROM rutas WHERE activo = true)::BIGINT,
        (SELECT COUNT(*) FROM asignacion_rutas)::BIGINT,
        (SELECT COUNT(*) FROM asignacion_rutas WHERE estado = 'ACTIVA' AND activo = true)::BIGINT,
        (SELECT COUNT(*) FROM incidentes)::BIGINT,
        (SELECT COUNT(*) FROM incidentes WHERE estado IN ('REPORTADO', 'EN_INVESTIGACION') AND activo = true)::BIGINT;
END;
$$;
