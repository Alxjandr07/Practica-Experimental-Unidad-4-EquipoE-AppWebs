-- =============================================================================
-- sp_incidentes_por_gravedad
-- Descripcion: Agrupa y cuenta incidentes por nivel de gravedad,
--              filtrados opcionalmente por tipo.
-- Uso: Agregacion con COUNT, GROUP BY
-- =============================================================================

CREATE OR REPLACE FUNCTION sp_incidentes_por_gravedad(
    p_tipo VARCHAR DEFAULT NULL
)
    RETURNS TABLE(
        gravedad VARCHAR,
        total_incidentes BIGINT,
        ultimo_incidente TIMESTAMPTZ
    )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT
        i.gravedad::VARCHAR,
        COUNT(*)::BIGINT AS total_incidentes,
        MAX(i.fecha_incidente)::TIMESTAMPTZ AS ultimo_incidente
    FROM incidentes i
    WHERE i.activo = true
      AND (p_tipo IS NULL OR i.tipo::TEXT = p_tipo)
    GROUP BY i.gravedad
    ORDER BY total_incidentes DESC;
END;
$$;
