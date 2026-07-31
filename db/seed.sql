-- =============================================================================
-- SGROAS — Datos Semilla
-- =============================================================================
-- Datos iniciales para desarrollo y pruebas.
-- Contrasena de admin: admin123 (BCrypt hash documentado)
-- BCrypt hash: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
-- =============================================================================

INSERT INTO usuarios (nombre, email, password_hash, rol, activo)
VALUES
  ('Admin SGROAS', 'admin@sgroas.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ROLE_ADMIN', true),
  ('Coordinador Principal', 'coordinador@sgroas.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ROLE_COORDINADOR', true),
  ('Seguridad General', 'seguridad@sgroas.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ROLE_SEGURIDAD', true);

-- Conductores de prueba
INSERT INTO conductores (nombres, apellidos, cedula, numero_licencia, tipo_licencia, fecha_vencimiento_licencia, telefono, email, estado)
VALUES
  ('Carlos', 'Perez', '1234567890', 'LIC-001', 'B', '2026-12-31', '0999000001', 'carlos@email.com', 'ACTIVO'),
  ('Maria', 'Lopez', '0987654321', 'LIC-002', 'C', '2026-11-15', '0999000002', 'maria@email.com', 'ACTIVO'),
  ('Pedro', 'Ramirez', '1112223334', 'LIC-003', 'B', '2027-03-20', '0999000003', 'pedro@email.com', 'ACTIVO'),
  ('Ana', 'Garcia', '5556667778', 'LIC-004', 'A', '2026-09-10', '0999000004', 'ana@email.com', 'SUSPENDIDO');
