package Hospital.citaMedica.service;

import Hospital.citaMedica.model.CitaMedicaModel;
import Hospital.citaMedica.repository.CitaMedicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CitaMedicaService {

    private final CitaMedicaRepository citaMedicaRepository;

    public List<CitaMedicaModel> findall(){
        return citaMedicaRepository.findAll();
    }

    public Optional<CitaMedicaModel> findById(Long id){
        return citaMedicaRepository.findById(id);
    }

    public CitaMedicaModel save(CitaMedicaModel citaMedicaModel){
        return citaMedicaRepository.save(citaMedicaModel);
    }

    public void delete(CitaMedicaModel citaMedicaModel){
        citaMedicaRepository.delete(citaMedicaModel);
    }

    public void deleteById(Long id){
        citaMedicaRepository.deleteById(id);
    }
}
