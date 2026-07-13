package Hospital.facturacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa el registro de facturación de una consulta médica")
public class Facturacion extends RepresentationModel<Facturacion>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único de la facturación",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private long id;

    @Column(length = 15)
    @NotBlank
    @Size(max = 15)
    @Schema(
            description = "Primer nombre del paciente",
            example = "Juan",
            maxLength = 15,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String priNombre;

    @Column(length = 15)
    @NotBlank
    @Size(max = 15)
    @Schema(
            description = "Apellido paterno del paciente",
            example = "Pérez",
            maxLength = 15,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String apPaterno;

    @Column(length = 25)
    @NotBlank
    @Size(max = 25)
    @Schema(
            description = "Tipo de consulta médica realizada",
            example = "Pediatría",
            maxLength = 25,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String tipoConsulta;

    @Column(length = 25)
    @NotBlank
    @Size(max = 25)
    @Schema(
            description = "Precio o costo de la consulta médica",
            example = "45000",
            maxLength = 25,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String precioConsulta;

    @Column(length = 10)
    @NotBlank
    @Size(max = 10)
    @Schema(
            description = "Fecha de la emisión de la facturación",
            example = "2026-06-19",
            maxLength = 10,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String fecha;
}
