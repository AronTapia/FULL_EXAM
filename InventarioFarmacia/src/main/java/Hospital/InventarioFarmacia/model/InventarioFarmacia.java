package Hospital.InventarioFarmacia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa el inventario de medicamentos en la farmacia del hospital")
public class InventarioFarmacia extends RepresentationModel<InventarioFarmacia> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único del registro de inventario",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private long id;

    @Column(length = 100)
    @NotBlank
    @Size(max = 100)
    @Schema(
            description = "Nombre comercial o genérico del medicamento",
            example = "Paracetamol",
            maxLength = 100,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String NombreMedicamento;

    @Schema(
            description = "Fecha de vencimiento del lote del medicamento",
            example = "2028-12-31"
    )
    private LocalDate fechaCaducidad;

    @Column(name = "cantidadMedicamento")
    @Min(0)
    @Max(100)
    @Schema(
            description = "Stock actual disponible del medicamento",
            example = "50",
            minimum = "0",
            maximum = "100"
    )
    private int cantidadMedicamento;

    @Column(name = "precioMedicamento")
    @NotNull
    @Min(0)
    @Max(100)
    @Schema(
            description = "Precio unitario del medicamento en la moneda local",
            example = "1500",
            minimum = "0",
            maximum = "100",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int precioMedicamento;
}
