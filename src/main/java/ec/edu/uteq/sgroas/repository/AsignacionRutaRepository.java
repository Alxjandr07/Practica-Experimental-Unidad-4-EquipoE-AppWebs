package ec.edu.uteq.sgroas.repository;

import ec.edu.uteq.sgroas.entity.AsignacionRuta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsignacionRutaRepository extends JpaRepository<AsignacionRuta, Long> {

    Page<AsignacionRuta> findByActivoTrue(Pageable pageable);

    List<AsignacionRuta> findByConductorIdAndActivoTrue(Long conductorId);

    List<AsignacionRuta> findByVehiculoIdAndActivoTrue(Long vehiculoId);

    List<AsignacionRuta> findByRutaIdAndActivoTrue(Long rutaId);
}
