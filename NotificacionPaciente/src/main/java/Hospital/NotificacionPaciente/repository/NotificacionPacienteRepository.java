package Hospital.NotificacionPaciente.repository;

import Hospital.NotificacionPaciente.model.NotificacionPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionPacienteRepository extends JpaRepository<NotificacionPaciente, Long> {
}
