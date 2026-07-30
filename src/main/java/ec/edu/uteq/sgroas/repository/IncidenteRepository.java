package ec.edu.uteq.sgroas.repository;

import ec.edu.uteq.sgroas.entity.Incidente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidenteRepository extends JpaRepository<Incidente, Long> {

    Page<Incidente> findByActivoTrue(Pageable pageable);

    List<Incidente> findByAsignacionIdAndActivoTrue(Long asignacionId);
}
