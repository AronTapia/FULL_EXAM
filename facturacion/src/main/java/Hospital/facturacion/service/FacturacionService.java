package Hospital.facturacion.service;

import Hospital.facturacion.model.Facturacion;
import Hospital.facturacion.repository.FacturacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FacturacionService {

    private final FacturacionRepository repository;

    public List<Facturacion> findAll(){
        return repository.findAll();
    }

    public Optional<Facturacion> findById(Long id){
        return repository.findById(id);
    }

    public Facturacion save (Facturacion facturacion){
        return repository.save(facturacion);
    }

    public void delete(Facturacion facturacion){
        repository.delete(facturacion);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
