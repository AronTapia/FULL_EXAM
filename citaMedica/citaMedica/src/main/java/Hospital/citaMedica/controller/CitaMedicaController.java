package Hospital.citaMedica.controller;

import Hospital.citaMedica.model.CitaMedicaModel;
import Hospital.citaMedica.service.CitaMedicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Importaciones estáticas necesarias para construir los enlaces de HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/citaMedica")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth") // Indicar que requiere autenticación a nivel de clase
public class CitaMedicaController {

    private final CitaMedicaService citaMedicaService;

    @Operation(summary = "Listar todas las citas médicas", description = "Obtiene una lista completa de todas las citas médicas registradas en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de citas médicas obtenida correctamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<CitaMedicaModel>> findAll(){
        List<CitaMedicaModel> citas = citaMedicaService.findall();

        // Añadimos los enlaces individuales a cada cita médica de la lista
        for (CitaMedicaModel cita : citas) {
            this.agregarEnlaces(cita);
        }

        // CollectionModel envuelve la lista y permite asignarle un enlace general (self) a la colección
        CollectionModel<CitaMedicaModel> model = CollectionModel.of(citas);
        model.add(linkTo(methodOn(CitaMedicaController.class).findAll()).withSelfRel());

        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Buscar cita médica por ID", description = "Obtiene los detalles de una cita médica específica mediante su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cita médica encontrada correctamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            @ApiResponse(responseCode = "404", description = "Cita médica no encontrada")
    })
    @GetMapping("{id}")
    public ResponseEntity<CitaMedicaModel> findById(@PathVariable Long id){
        return citaMedicaService.findById(id)
                .map(cita -> {
                    this.agregarEnlaces(cita);
                    return ResponseEntity.ok(cita);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear cita médica", description = "Registra una nueva cita médica en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cita médica creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @PostMapping
    public ResponseEntity<CitaMedicaModel> save(@Valid @RequestBody CitaMedicaModel citaMedicaModel){
        CitaMedicaModel citaGuardada = citaMedicaService.save(citaMedicaModel);
        this.agregarEnlaces(citaGuardada);
        return ResponseEntity.status(HttpStatus.CREATED).body(citaGuardada);
    }

    @Operation(summary = "Actualizar cita médica", description = "Actualiza los datos de una cita médica existente en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cita médica actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @PutMapping
    public ResponseEntity<CitaMedicaModel> update(@Valid @RequestBody CitaMedicaModel citaMedicaModel){
        CitaMedicaModel citaActualizada = citaMedicaService.save(citaMedicaModel);
        this.agregarEnlaces(citaActualizada);
        return ResponseEntity.ok(citaActualizada);
    }

    @Operation(summary = "Eliminar cita médica (Modelo)", description = "Elimina una cita médica del sistema enviando el modelo completo en la petición.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cita médica eliminada correctamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody CitaMedicaModel citaMedicaModel){
        citaMedicaService.delete(citaMedicaModel);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Eliminar cita médica por ID", description = "Elimina una cita médica específica del sistema mediante su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cita médica eliminada correctamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        citaMedicaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Método auxiliar privado para reutilizar la lógica de inyección de enlaces hipermedia.
     * Limpia los enlaces previos antes de añadir los nuevos para evitar duplicados en operaciones secuenciales.
     */
    private void agregarEnlaces(CitaMedicaModel cita) {
        cita.removeLinks(); // Previene enlaces duplicados si el objeto pasa por múltiples procesos

        cita.add(linkTo(methodOn(CitaMedicaController.class).findById(cita.getId())).withSelfRel());
        cita.add(linkTo(methodOn(CitaMedicaController.class).findAll()).withRel("todas-las-citas-medicas"));
        cita.add(linkTo(methodOn(CitaMedicaController.class).update(cita)).withRel("actualizar"));
        cita.add(linkTo(methodOn(CitaMedicaController.class).deleteById(cita.getId())).withRel("eliminar-por-id"));
    }
}
