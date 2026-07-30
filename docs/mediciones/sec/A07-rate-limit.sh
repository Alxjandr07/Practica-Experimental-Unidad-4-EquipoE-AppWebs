#!/bin/bash
# A07 - Fallo de identificacion y autenticacion
# Verifica rate limiting: 6 intentos fallidos -> 429 Too Many Requests

echo "=== A07: Rate limiting ==="

for i in $(seq 1 6); do
  echo "--- Intento $i ---"
  STATUS=$(curl -s -o /dev/null -w "%{http_code}" -X POST http://localhost:8080/api/auth/login \
    -H "Content-Type: application/json" \
    -d '{"email":"admin@sgroas.com","password":"wrongpass"}')
  echo "HTTP $STATUS"
done

echo ""
echo "--- Resultado esperado: intento 6 -> 429 Too Many Requests ---"
