package Hospital.historialClinico.repository;

import Hospital.historialClinico.model.HistorialClinicoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialClinicoRepository extends JpaRepository<HistorialClinicoModel, Long> {
}
