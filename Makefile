.PHONY: up down test bench audit clean

# =============================================================================
# SGROAS — Makefile
# =============================================================================
# Uso: make up    -> levantar el sistema completo
#      make down  -> detener contenedores
#      make test  -> ejecutar pruebas
#      make bench -> ejecutar benchmarks k6
#      make audit -> auditoría OWASP
#      make clean -> limpieza total
# =============================================================================

up:
	docker compose up --build -d
	@echo "Esperando a que el backend esté listo..."
	@sleep 15
	@echo "Sistema disponible en http://localhost:8080"

down:
	docker compose down -v

test:
	docker compose run --rm backend ./mvnw test

bench:
	@echo "Ejecutando benchmarks k6 (3 corridas)..."
	k6 run --vus 50 --duration 30s k6/script.js --seed 42 --summary-export docs/mediciones/perf/kNN-run1.json
	k6 run --vus 50 --duration 30s k6/script.js --seed 42 --summary-export docs/mediciones/perf/kNN-run2.json
	k6 run --vus 50 --duration 30s k6/script.js --seed 42 --summary-export docs/mediciones/perf/kNN-run3.json
	@echo "Benchmarks completos. Resultados en docs/mediciones/perf/"

audit:
	@echo "Ejecutando auditoría OWASP..."
	scripts/audit-sql-dynamic.sh
	@echo "Verificar resultados en docs/mediciones/sec/"

clean:
	docker compose down -v --rmi all
	@echo "Limpieza completada."
