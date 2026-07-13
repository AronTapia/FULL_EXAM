package Hospital.citaMedica.repository;

import Hospital.citaMedica.model.CitaMedicaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaMedicaRepository extends JpaRepository<CitaMedicaModel, Long> {
}
