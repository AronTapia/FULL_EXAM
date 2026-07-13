package Hospital.historialClinico.model;

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
@Schema(description = "Entidad que representa el historial clínico de un paciente dentro del hospital")
public class HistorialClinicoModel extends RepresentationModel<HistorialClinicoModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único del historial clínico",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
            description = "Nombres del paciente",
            example = "Juan Carlos",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String nombres;

    @Schema(
            description = "Apellidos del paciente",
            example = "Pérez Gómez",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String apellidos;

    @Schema(
            description = "Fecha de nacimiento del paciente (formato YYYY-MM-DD)",
            example = "1985-08-24",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String fechaNacimiento;

    @Schema(
            description = "Dirección de residencia del paciente",
            example = "Av. Providencia 1234",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String direccion;

    @Schema(
            description = "Número de teléfono de contacto del paciente",
            example = "912345678",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int telefono;
}