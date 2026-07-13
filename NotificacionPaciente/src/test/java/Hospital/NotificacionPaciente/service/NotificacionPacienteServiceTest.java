package Hospital.NotificacionPaciente.service;

import Hospital.NotificacionPaciente.model.NotificacionPaciente;
import Hospital.NotificacionPaciente.repository.NotificacionPacienteRepository;
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
class NotificacionPacienteServiceTest {

    @Mock
    private NotificacionPacienteRepository repository;

    @InjectMocks
    private NotificacionPacienteService service;

    private NotificacionPaciente notificacion;

    @BeforeEach
    void setUp() {
        notificacion = new NotificacionPaciente(
                1L,
                101L,
                "12345678-9",
                "Juan Pérez",
                "Dr. Carlos Astudillo",
                LocalDate.of(2026, 5, 15),
                "Su receta médica ya está disponible para retiro."
        );
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(notificacion));

        List<NotificacionPaciente> resultado = service.findAll();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombrePaciente()).isEqualTo("Juan Pérez");
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.of(notificacion));

        Optional<NotificacionPaciente> resultado = service.findById(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getRutPaciente()).isEqualTo("12345678-9");
    }

    @Test
    void findByIdVacio() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<NotificacionPaciente> resultado = service.findById(99L);

        assertThat(resultado).isEmpty();
    }

    @Test
    void save() {
        when(repository.save(notificacion)).thenReturn(notificacion);

        NotificacionPaciente resultado = service.save(notificacion);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getMedicoResponsable()).isEqualTo("Dr. Carlos Astudillo");
        verify(repository).save(notificacion);
    }

    @Test
    void delete() {
        service.delete(notificacion);

        verify(repository, times(1)).delete(notificacion);
    }

    @Test
    void deleteById() {
        service.deleteById(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
