package Hospital.facturacion.service;

import Hospital.facturacion.model.Facturacion;
import Hospital.facturacion.repository.FacturacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FacturacionServiceTest {

    @Mock
    private FacturacionRepository repository;

    @InjectMocks
    private FacturacionService service;

    private Facturacion facturacion;

    @BeforeEach
    void setUp() {
        facturacion = new Facturacion(1L, "Juan", "Pérez", "Consulta General", "45000", "2026-06-19");
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(facturacion));

        List<Facturacion> resultado = service.findAll();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getPriNombre()).isEqualTo("Juan");
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.of(facturacion));

        Optional<Facturacion> resultado = service.findById(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(1L);
    }

    @Test
    void findByIdVacio() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<Facturacion> resultado = service.findById(99L);

        assertThat(resultado).isEmpty();
    }

    @Test
    void save() {
        when(repository.save(facturacion)).thenReturn(facturacion);

        Facturacion resultado = service.save(facturacion);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getTipoConsulta()).isEqualTo("Consulta General");
        verify(repository).save(facturacion);
    }

    @Test
    void delete() {
        service.delete(facturacion);

        verify(repository, times(1)).delete(facturacion);
    }

    @Test
    void deleteById() {
        service.deleteById(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
