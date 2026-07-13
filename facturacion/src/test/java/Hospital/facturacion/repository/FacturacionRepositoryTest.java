package Hospital.facturacion.repository;

import Hospital.facturacion.model.Facturacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FacturacionRepositoryTest {

    @Autowired
    private FacturacionRepository repository;
    @BeforeEach
    void limpiarTabla() {
        repository.deleteAll();
    }
    @Test
    void save() {
        Facturacion f = new Facturacion(0, "Pedro", "Soto", "Traumatología", "50000", "2026-07-10");

        Facturacion guardada = repository.save(f);

        assertThat(guardada.getId()).isPositive();
    }

    @Test
    void findById() {
        Facturacion f = new Facturacion(0, "Laura", "Vega", "Dermatología", "40000", "2026-07-05");
        Facturacion guardada = repository.save(f);

        Optional<Facturacion> encontrada = repository.findById(guardada.getId());

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getPriNombre()).isEqualTo("Laura");
    }

    @Test
    void findAll() {
        repository.save(new Facturacion(0, "Ana", "Ruiz", "Pediatría", "30000", "2026-07-01"));
        repository.save(new Facturacion(0, "Luis", "Marín", "Oftalmología", "35000", "2026-07-02"));

        List<Facturacion> todas = repository.findAll();

        assertThat(todas).hasSize(2);
    }

    @Test
    void deleteById() {
        Facturacion guardada = repository.save(
                new Facturacion(0, "Sofía", "Torres", "Nutrición", "20000", "2026-07-03"));

        repository.deleteById(guardada.getId());

        assertThat(repository.findById(guardada.getId())).isEmpty();
    }
}
