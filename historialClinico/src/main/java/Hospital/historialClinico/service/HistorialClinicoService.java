package Hospital.historialClinico.service;

import Hospital.historialClinico.model.HistorialClinicoModel;
import Hospital.historialClinico.repository.HistorialClinicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HistorialClinicoService {

    private final HistorialClinicoRepository clinicoRepository;

    public List<HistorialClinicoModel> findall(){
        return clinicoRepository.findAll();
    }

    public Optional<HistorialClinicoModel> findById(Long id){
        return clinicoRepository.findById(id);
    }

    public HistorialClinicoModel save(HistorialClinicoModel historialClinicoModel){
        return clinicoRepository.save(historialClinicoModel);
    }

    public void delete(HistorialClinicoModel historialClinicoModel){
        clinicoRepository.delete(historialClinicoModel);
    }

    public void deleteById(Long id){
        clinicoRepository.deleteById(id);
    }
}
