package Hospital.InventarioFarmacia.service;

import Hospital.InventarioFarmacia.model.InventarioFarmacia;
import Hospital.InventarioFarmacia.repository.InventarioFarmaciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class InventarioFarmaciaServiceTest {

    @Mock
    private InventarioFarmaciaRepository repository;

    @InjectMocks
    private InventarioFarmaciaService service;

    private InventarioFarmacia medicamento;

    @BeforeEach
    void setUp() {
        medicamento = new InventarioFarmacia(
                1L, "Paracetamol 500mg", LocalDate.of(2027, 5, 15), 50, 5);
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(medicamento));

        List<InventarioFarmacia> resultado = service.findAll();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombreMedicamento()).isEqualTo("Paracetamol 500mg");
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.of(medicamento));

        Optional<InventarioFarmacia> resultado = service.findById(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getCantidadMedicamento()).isEqualTo(50);
    }

    @Test
    void findByIdVacio() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<InventarioFarmacia> resultado = service.findById(99L);

        assertThat(resultado).isEmpty();
    }

    @Test
    void save() {
        when(repository.save(medicamento)).thenReturn(medicamento);

        InventarioFarmacia resultado = service.save(medicamento);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getPrecioMedicamento()).isEqualTo(5);
        verify(repository).save(medicamento);
    }

    @Test
    void delete() {
        service.delete(medicamento);

        verify(repository, times(1)).delete(medicamento);
    }

    @Test
    void deleteById() {
        service.deleteById(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
