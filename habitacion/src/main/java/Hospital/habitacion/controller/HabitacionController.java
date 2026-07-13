package Hospital.habitacion.controller;

import Hospital.habitacion.model.HabitacionModel;
import Hospital.habitacion.service.HabitacionService;
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
@RequestMapping("/api/v1/habitacion")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth") // Indicar que requiere autenticación a nivel de clase
public class HabitacionController {

    private final HabitacionService habitacionService;

    @Operation(summary = "Listar todas las habitaciones", description = "Obtiene una lista completa de todas las habitaciones registradas en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de habitaciones obtenida correctamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<HabitacionModel>> findAll(){
        List<HabitacionModel> habitaciones = habitacionService.findall();

        // Añadimos los enlaces individuales a cada habitación de la lista
        for (HabitacionModel hab : habitaciones) {
            this.agregarEnlaces(hab);
        }

        // CollectionModel envuelve la lista y permite asignarle un enlace general (self) a la colección
        CollectionModel<HabitacionModel> model = CollectionModel.of(habitaciones);
        model.add(linkTo(methodOn(HabitacionController.class).findAll()).withSelfRel());

        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Buscar habitación por ID", description = "Obtiene los detalles de una habitación específica mediante su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habitación encontrada correctamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            @ApiResponse(responseCode = "404", description = "Habitación no encontrada")
    })
    @GetMapping("{id}")
    public ResponseEntity<HabitacionModel> findById(@PathVariable Long id){
        return habitacionService.findById(id)
                .map(hab -> {
                    this.agregarEnlaces(hab);
                    return ResponseEntity.ok(hab);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear habitación", description = "Registra una nueva habitación en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Habitación creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @PostMapping
    public ResponseEntity<HabitacionModel> save(@Valid @RequestBody HabitacionModel habitacionModel){
        HabitacionModel habGuardada = habitacionService.save(habitacionModel);
        this.agregarEnlaces(habGuardada);
        return ResponseEntity.status(HttpStatus.CREATED).body(habGuardada);
    }

    @Operation(summary = "Actualizar habitación", description = "Actualiza los datos de una habitación existente en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habitación actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @PutMapping
    public ResponseEntity<HabitacionModel> update(@Valid @RequestBody HabitacionModel habitacionModel){
        HabitacionModel habActualizada = habitacionService.save(habitacionModel);
        this.agregarEnlaces(habActualizada);
        return ResponseEntity.ok(habActualizada);
    }

    @Operation(summary = "Eliminar habitación (Modelo)", description = "Elimina una habitación del sistema enviando el modelo completo en la petición.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Habitación eliminada correctamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody HabitacionModel habitacionModel){
        habitacionService.delete(habitacionModel);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Eliminar habitación por ID", description = "Elimina una habitación específica del sistema mediante su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Habitación eliminada correctamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        habitacionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Método auxiliar privado para reutilizar la lógica de inyección de enlaces hipermedia.
     * Limpia los enlaces previos antes de añadir los nuevos para evitar duplicados en operaciones secuenciales.
     */
    private void agregarEnlaces(HabitacionModel hab) {
        hab.removeLinks(); // Previene enlaces duplicados si el objeto pasa por múltiples procesos

        hab.add(linkTo(methodOn(HabitacionController.class).findById(hab.getId())).withSelfRel());
        hab.add(linkTo(methodOn(HabitacionController.class).findAll()).withRel("todas-las-habitaciones"));
        hab.add(linkTo(methodOn(HabitacionController.class).update(hab)).withRel("actualizar"));
        hab.add(linkTo(methodOn(HabitacionController.class).deleteById(hab.getId())).withRel("eliminar-por-id"));
    }
}