package Hospital.NotificacionPaciente.repository;

import Hospital.NotificacionPaciente.model.NotificacionPaciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class NotificacionPacienteRepositoryTest {

    @Autowired
    private NotificacionPacienteRepository repository;
    @BeforeEach
    void limpiarTabla() {
        repository.deleteAll();
    }
    @Test
    void save() {
        NotificacionPaciente n = new NotificacionPaciente(
                0, 103L, "15987412-K", "Andrés Morales", "Dr. Carlos Astudillo",
                LocalDate.of(2026, 5, 16), "Su orden de exámenes de laboratorio ha sido emitida.");

        NotificacionPaciente guardada = repository.save(n);

        assertThat(guardada.getId()).isPositive();
    }

    @Test
    void findById() {
        NotificacionPaciente n = new NotificacionPaciente(
                0, 104L, "19222333-4", "Laura Vega", "Dra. Ana María Silva",
                LocalDate.of(2026, 5, 18), "Su hora de control fue reagendada.");
        NotificacionPaciente guardada = repository.save(n);

        Optional<NotificacionPaciente> encontrada = repository.findById(guardada.getId());

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getNombrePaciente()).isEqualTo("Laura Vega");
    }

    @Test
    void findAll() {
        repository.save(new NotificacionPaciente(
                0, 105L, "20111222-3", "Luis Marín", "Dr. Carlos Astudillo",
                LocalDate.of(2026, 5, 19), "Recuerde traer sus exámenes previos."));
        repository.save(new NotificacionPaciente(
                0, 106L, "21333444-5", "Sofía Torres", "Dra. Ana María Silva",
                LocalDate.of(2026, 5, 20), "Su cita fue confirmada."));

        List<NotificacionPaciente> todas = repository.findAll();

        assertThat(todas).hasSize(2);
    }

    @Test
    void deleteById() {
        NotificacionPaciente guardada = repository.save(new NotificacionPaciente(
                0, 107L, "22444555-6", "Pedro Soto", "Dr. Carlos Astudillo",
                LocalDate.of(2026, 5, 21), "Su tratamiento ha finalizado."));

        repository.deleteById(guardada.getId());

        assertThat(repository.findById(guardada.getId())).isEmpty();
    }
}
