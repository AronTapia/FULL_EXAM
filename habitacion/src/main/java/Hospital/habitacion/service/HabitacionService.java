package Hospital.habitacion.service;

import Hospital.habitacion.model.HabitacionModel;
import Hospital.habitacion.repository.HabitacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;

    public List<HabitacionModel> findall(){
        return habitacionRepository.findAll();
    }

    public Optional<HabitacionModel> findById(Long id){
        return habitacionRepository.findById(id);
    }

    public HabitacionModel save(HabitacionModel habitacionModel){
        return habitacionRepository.save(habitacionModel);
    }

    public void delete(HabitacionModel habitacionModel){
        habitacionRepository.delete(habitacionModel);
    }

    public void deleteById(Long id){
        habitacionRepository.deleteById(id);
    }
}
