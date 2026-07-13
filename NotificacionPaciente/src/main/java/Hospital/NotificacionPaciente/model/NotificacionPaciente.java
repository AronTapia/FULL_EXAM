package Hospital.NotificacionPaciente.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Entidad que representa una notificación enviada a un paciente registrado en el sistema")
public class NotificacionPaciente extends RepresentationModel<NotificacionPaciente> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único de la notificación",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private long id;

    @NotNull
    @Schema(
            description = "Identificador único del paciente asociado",
            example = "12",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long idPaciente;

    @Column(length = 100)
    @NotBlank
    @Size(max = 100)
    @Schema(
            description = "RUT del paciente que recibe la notificación",
            example = "12345678-9",
            maxLength = 100,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String rutPaciente;

    @Column(length = 100)
    @NotBlank
    @Size(max = 100)
    @Schema(
            description = "Nombre completo del paciente",
            example = "Juan Pérez Gómez",
            maxLength = 100,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String nombrePaciente;

    @Column(length = 100)
    @NotBlank
    @Size(max = 100)
    @Schema(
            description = "Nombre del médico que emite o es responsable de la notificación",
            example = "Dr. Carlos Astudillo",
            maxLength = 100,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String medicoResponsable;

    @Schema(
            description = "Fecha de emisión de la notificación",
            example = "2026-06-21"
    )
    private LocalDate fecha;

    @Column(length = 250)
    @NotBlank
    @Size(max = 250)
    @Schema(
            description = "Contenido o cuerpo del mensaje enviado al paciente",
            example = "Se le recuerda asistir a su consulta médica programada.",
            maxLength = 250,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String mensaje;
}
