package Hospital.NotificacionPaciente.service;

import Hospital.NotificacionPaciente.model.NotificacionPaciente;
import Hospital.NotificacionPaciente.repository.NotificacionPacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificacionPacienteService {

    private final NotificacionPacienteRepository repository;

    public List<NotificacionPaciente> findAll(){
        return repository.findAll();
    }

    public Optional<NotificacionPaciente> findById(Long id){
        return repository.findById(id);
    }

    public NotificacionPaciente save (NotificacionPaciente notificacionPaciente){
        return repository.save(notificacionPaciente);
    }

    public void delete(NotificacionPaciente notificacionPaciente){
        repository.delete(notificacionPaciente);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
