package Hospital.InventarioFarmacia.repository;

import Hospital.InventarioFarmacia.model.InventarioFarmacia;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class InventarioFarmaciaRepositoryTest {

    @Autowired
    private InventarioFarmaciaRepository repository;

    @Test
    void save() {
        InventarioFarmacia m = new InventarioFarmacia(
                0, "Amoxicilina 875mg", LocalDate.of(2028, 2, 20), 15, 25);

        InventarioFarmacia guardado = repository.save(m);

        assertThat(guardado.getId()).isPositive();
    }

    @Test
    void findById() {
        InventarioFarmacia m = new InventarioFarmacia(
                0, "Loratadina 10mg", LocalDate.of(2027, 3, 10), 20, 4);
        InventarioFarmacia guardado = repository.save(m);

        Optional<InventarioFarmacia> encontrado = repository.findById(guardado.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNombreMedicamento()).isEqualTo("Loratadina 10mg");
    }

    @Test
    void findAll() {
        repository.save(new InventarioFarmacia(0, "Omeprazol 20mg", LocalDate.of(2027, 6, 1), 25, 7));
        repository.save(new InventarioFarmacia(0, "Metformina 850mg", LocalDate.of(2027, 8, 15), 10, 6));

        List<InventarioFarmacia> todos = repository.findAll();

        assertThat(todos).hasSize(2);
    }

    @Test
    void deleteById() {
        InventarioFarmacia guardado = repository.save(
                new InventarioFarmacia(0, "Salbutamol Inhalador", LocalDate.of(2027, 1, 5), 12, 15));

        repository.deleteById(guardado.getId());

        assertThat(repository.findById(guardado.getId())).isEmpty();
    }
}
