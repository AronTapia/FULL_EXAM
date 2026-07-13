package Hospital.citaMedica.model;

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
@Schema(description = "Entidad que representa una cita médica dentro del hospital")
public class CitaMedicaModel extends RepresentationModel<CitaMedicaModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único de la cita médica",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
            description = "Tipo de la cita médica (por ejemplo: Consulta general, Especialista, Examen)",
            example = "Consulta general",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String tipo;

    @Schema(
            description = "Fecha y hora en la que está programada la cita",
            example = "2026-06-25T14:30:00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String fecha;

    @Schema(
            description = "Ubicación o consultorio asignado para la cita",
            example = "Consultorio 204, Pabellón B",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String ubicacion;

    @Schema(
            description = "Nombre del doctor o especialista asignado a la cita médica",
            example = "Dra. María González",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String nombreDoctor;
}
