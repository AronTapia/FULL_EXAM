package Hospital.habitacion.repository;

import Hospital.habitacion.model.HabitacionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitacionRepository extends JpaRepository<HabitacionModel, Long> {
}
