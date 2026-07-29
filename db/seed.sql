-- =============================================================================
-- SGROAS — Datos Semilla
-- =============================================================================
-- Datos iniciales para desarrollo y pruebas.
-- Contraseña de admin: admin123 (BCrypt hash documentado)
-- BCrypt hash: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
-- =============================================================================

INSERT INTO usuarios (nombre, email, password_hash, rol, activo)
VALUES
  ('Admin SGROAS', 'admin@sgroas.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ROLE_ADMIN', true),
  ('Coordinador Principal', 'coordinador@sgroas.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ROLE_COORDINADOR', true),
  ('Seguridad General', 'seguridad@sgroas.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ROLE_SEGURIDAD', true);
