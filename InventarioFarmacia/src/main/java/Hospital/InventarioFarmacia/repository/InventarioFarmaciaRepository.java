package Hospital.InventarioFarmacia.repository;

import Hospital.InventarioFarmacia.model.InventarioFarmacia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioFarmaciaRepository extends JpaRepository<InventarioFarmacia, Long> {
}
