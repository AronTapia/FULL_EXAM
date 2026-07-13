package Hospital.InventarioFarmacia.service;

import Hospital.InventarioFarmacia.model.InventarioFarmacia;
import Hospital.InventarioFarmacia.repository.InventarioFarmaciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventarioFarmaciaService {

    private final InventarioFarmaciaRepository repository;

    public List<InventarioFarmacia> findAll(){
        return repository.findAll();
    }

    public Optional<InventarioFarmacia> findById(Long id){
        return repository.findById(id);
    }

    public InventarioFarmacia save (InventarioFarmacia inventarioFarmacia){
        return repository.save(inventarioFarmacia);
    }

    public void delete(InventarioFarmacia inventarioFarmacia){
        repository.delete(inventarioFarmacia);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
