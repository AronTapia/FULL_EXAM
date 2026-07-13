package Hospital.NotificacionPaciente.controller;

import Hospital.NotificacionPaciente.model.NotificacionPaciente;
import Hospital.NotificacionPaciente.service.NotificacionPacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/notificacionPaciente")
@RequiredArgsConstructor
@Tag(name = "Notificaciones de Pacientes", description = "Controlador para la gestión y envío de alertas o mensajes a los pacientes")
public class NotificacionPacienteController {

    private final NotificacionPacienteService service;

    @Operation(
            summary = "Obtener todas las notificaciones",
            description = "Retorna una lista con todas las notificaciones registradas en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping
    public ResponseEntity<List<NotificacionPaciente>> findAll(){
        List<NotificacionPaciente> lista = service.findAll();
        for (NotificacionPaciente elemento : lista) {
            elemento.add(linkTo(methodOn(NotificacionPacienteController.class).findById(elemento.getId())).withSelfRel());
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(
            summary = "Buscar notificación por ID",
            description = "Busca y retorna una única notificación basada en su identificador numérico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificación encontrada"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @GetMapping("{id}")
    public ResponseEntity<NotificacionPaciente> findById(@PathVariable Long id){
        return service.findById(id)
                .map(elemento -> {
                    elemento.add(linkTo(methodOn(NotificacionPacienteController.class).findAll()).withRel("todos"));
                    elemento.add(linkTo(methodOn(NotificacionPacienteController.class).findById(elemento.getId())).withSelfRel());
                    elemento.add(linkTo(methodOn(NotificacionPacienteController.class).update(elemento)).withRel("update"));
                    elemento.add(linkTo(methodOn(NotificacionPacienteController.class).deleteById(elemento.getId())).withRel("delete"));
                    return ResponseEntity.ok(elemento);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear notificación de paciente",
            description = "Registra y emite un nuevo mensaje o alerta destinado a un paciente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificación creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<NotificacionPaciente> save(@Valid @RequestBody NotificacionPaciente notificacionPaciente){
        NotificacionPaciente elemento = service.save(notificacionPaciente);
        elemento.add(linkTo(methodOn(NotificacionPacienteController.class).findAll()).withRel("todos"));
        elemento.add(linkTo(methodOn(NotificacionPacienteController.class).findById(elemento.getId())).withSelfRel());
        elemento.add(linkTo(methodOn(NotificacionPacienteController.class).update(elemento)).withRel("update"));
        elemento.add(linkTo(methodOn(NotificacionPacienteController.class).deleteById(elemento.getId())).withRel("delete"));
        return ResponseEntity.status(HttpStatus.CREATED).body(elemento);
    }

    @Operation(
            summary = "Actualizar notificación",
            description = "Modifica el contenido o datos de una notificación existente enviada en el cuerpo."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificación actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @PutMapping
    public ResponseEntity<NotificacionPaciente> update(@RequestBody NotificacionPaciente notificacionPaciente){
        NotificacionPaciente elemento = service.save(notificacionPaciente);
        elemento.add(linkTo(methodOn(NotificacionPacienteController.class).findAll()).withRel("todos"));
        elemento.add(linkTo(methodOn(NotificacionPacienteController.class).findById(elemento.getId())).withSelfRel());
        elemento.add(linkTo(methodOn(NotificacionPacienteController.class).deleteById(elemento.getId())).withRel("delete"));
        return ResponseEntity.ok(elemento);
    }

    @Operation(
            summary = "Eliminar notificación por objeto",
            description = "Elimina un registro de notificación pasando la entidad completa en el cuerpo."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notificación eliminada con éxito")
    })
    @DeleteMapping
    public ResponseEntity delete(@RequestBody NotificacionPaciente notificacionPaciente){
        service.delete(notificacionPaciente);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Eliminar notificación por ID",
            description = "Elimina permanentemente una notificación del sistema utilizando su identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notificación eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "ID no encontrado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
