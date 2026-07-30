-- =============================================================================
-- sp_obtener_incidentes_por_rango
-- Descripcion: Obtiene todos los incidentes en un rango de fechas con
--              informacion del conductor, vehiculo y ruta asociados.
-- JOINs: incidentes + asignacion_rutas + conductores + vehiculos + rutas
-- =============================================================================

CREATE OR REPLACE FUNCTION sp_obtener_incidentes_por_rango(
    p_fecha_desde TIMESTAMPTZ,
    p_fecha_hasta TIMESTAMPTZ
)
    RETURNS TABLE(
        incidente_id BIGINT,
        tipo VARCHAR,
        gravedad VARCHAR,
        estado VARCHAR,
        descripcion TEXT,
        fecha_incidente TIMESTAMPTZ,
        ubicacion VARCHAR,
        conductor_nombre VARCHAR,
        vehiculo_placa VARCHAR,
        ruta_codigo VARCHAR
    )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT
        i.id,
        i.tipo::VARCHAR,
        i.gravedad::VARCHAR,
        i.estado::VARCHAR,
        i.descripcion,
        i.fecha_incidente,
        i.ubicacion,
        (c.nombres || ' ' || c.apellidos)::VARCHAR AS conductor_nombre,
        v.placa::VARCHAR,
        r.codigo::VARCHAR
    FROM incidentes i
             JOIN asignacion_rutas ar ON i.asignacion_id = ar.id
             JOIN conductores c ON ar.conductor_id = c.id
             JOIN vehiculos v ON ar.vehiculo_id = v.id
             JOIN rutas r ON ar.ruta_id = r.id
    WHERE i.activo = true
      AND i.fecha_incidente >= p_fecha_desde
      AND i.fecha_incidente <= p_fecha_hasta
    ORDER BY i.fecha_incidente DESC;
END;
$$;
