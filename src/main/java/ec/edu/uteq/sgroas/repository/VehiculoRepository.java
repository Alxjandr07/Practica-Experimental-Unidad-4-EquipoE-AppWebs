package ec.edu.uteq.sgroas.repository;

import ec.edu.uteq.sgroas.entity.Vehiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    Page<Vehiculo> findByActivoTrue(Pageable pageable);

    boolean existsByPlaca(String placa);
}
