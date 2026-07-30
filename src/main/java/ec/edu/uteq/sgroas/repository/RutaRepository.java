package ec.edu.uteq.sgroas.repository;

import ec.edu.uteq.sgroas.entity.Ruta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RutaRepository extends JpaRepository<Ruta, Long> {

    Page<Ruta> findByActivoTrue(Pageable pageable);

    boolean existsByCodigo(String codigo);
}
