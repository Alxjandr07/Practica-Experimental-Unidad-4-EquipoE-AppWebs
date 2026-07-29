-- V2__seed.sql
-- Datos semilla para desarrollo
-- BCrypt hash de "admin123": $2b$10$4Xg/hLS584uT0/7GaKFuWOp2hMi9ym15xrCjRhZmVM5KPzGkQLYZW

INSERT INTO usuarios (nombre, email, password_hash, rol, activo)
VALUES
  ('Admin SGROAS', 'admin@sgroas.com', '$2b$10$4Xg/hLS584uT0/7GaKFuWOp2hMi9ym15xrCjRhZmVM5KPzGkQLYZW', 'ROLE_ADMIN', true),
  ('Coordinador Principal', 'coordinador@sgroas.com', '$2b$10$4Xg/hLS584uT0/7GaKFuWOp2hMi9ym15xrCjRhZmVM5KPzGkQLYZW', 'ROLE_COORDINADOR', true),
  ('Seguridad General', 'seguridad@sgroas.com', '$2b$10$4Xg/hLS584uT0/7GaKFuWOp2hMi9ym15xrCjRhZmVM5KPzGkQLYZW', 'ROLE_SEGURIDAD', true)
ON CONFLICT DO NOTHING;
