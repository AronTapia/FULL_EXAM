package Hospital.habitacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Entidad que representa una habitación dentro del hospital")
public class HabitacionModel extends RepresentationModel<HabitacionModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único de la habitación",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
            description = "Tipo de la habitación (por ejemplo: Individual, Compartida, UCI)",
            example = "Individual",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String tipo;

    @Schema(
            description = "Capacidad máxima de pacientes en la habitación",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String capacidad;

    @Schema(
            description = "Estado actual de disponibilidad de la habitación",
            example = "Disponible",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String disponibilidad;

    @Schema(
            description = "Costo base de la habitación por día",
            example = "45000.0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private double costoDia;
}