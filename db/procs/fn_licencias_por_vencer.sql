-- =============================================================================
-- fn_licencias_por_vencer
-- Descripcion: Funcion que retorna los conductores cuya licencia vence
--              dentro de los proximos N dias.
-- Uso: Funcion con parametro, filtro por fecha
-- =============================================================================

CREATE OR REPLACE FUNCTION fn_licencias_por_vencer(
    p_dias_umbral INTEGER DEFAULT 30
)
    RETURNS TABLE(
        conductor_id BIGINT,
        nombre_completo VARCHAR,
        cedula VARCHAR,
        numero_licencia VARCHAR,
        tipo_licencia VARCHAR,
        fecha_vencimiento DATE,
        asignacion_activa BOOLEAN
    )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT
        c.id,
        (c.nombres || ' ' || c.apellidos)::VARCHAR AS nombre_completo,
        c.cedula::VARCHAR,
        c.numero_licencia::VARCHAR,
        c.tipo_licencia::VARCHAR,
        c.fecha_vencimiento_licencia,
        EXISTS(
            SELECT 1 FROM asignacion_rutas ar
            WHERE ar.conductor_id = c.id
              AND ar.estado = 'ACTIVA'
              AND ar.activo = true
        )::BOOLEAN AS asignacion_activa
    FROM conductores c
    WHERE c.activo = true
      AND c.fecha_vencimiento_licencia BETWEEN CURRENT_DATE AND (CURRENT_DATE + p_dias_umbral)
    ORDER BY c.fecha_vencimiento_licencia;
END;
$$;
