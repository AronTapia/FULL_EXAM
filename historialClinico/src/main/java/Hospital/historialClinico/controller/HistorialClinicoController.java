package Hospital.historialClinico.controller;

import Hospital.historialClinico.model.HistorialClinicoModel;
import Hospital.historialClinico.service.HistorialClinicoService;
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
@RequestMapping("/api/v1/historialClinico")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth") // Indicar que requiere autenticación a nivel de clase
public class HistorialClinicoController {

    private final HistorialClinicoService clinicoService;

    @Operation(summary = "Listar todos los historiales clínicos", description = "Obtiene una lista completa de todos los historiales clínicos registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de historiales clínicos obtenida correctamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<HistorialClinicoModel>> findAll(){
        List<HistorialClinicoModel> historiales = clinicoService.findall();

        // Añadimos los enlaces individuales a cada historial clínico de la lista
        for (HistorialClinicoModel hist : historiales) {
            this.agregarEnlaces(hist);
        }

        // CollectionModel envuelve la lista y permite asignarle un enlace general (self) a la colección
        CollectionModel<HistorialClinicoModel> model = CollectionModel.of(historiales);
        model.add(linkTo(methodOn(HistorialClinicoController.class).findAll()).withSelfRel());

        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Buscar historial clínico por ID", description = "Obtiene los detalles de un historial clínico específico mediante su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial clínico encontrado correctamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            @ApiResponse(responseCode = "404", description = "Historial clínico no encontrado")
    })
    @GetMapping("{id}")
    public ResponseEntity<HistorialClinicoModel> findById(@PathVariable Long id){
        return clinicoService.findById(id)
                .map(hist -> {
                    this.agregarEnlaces(hist);
                    return ResponseEntity.ok(hist);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear historial clínico", description = "Registra un nuevo historial clínico en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Historial clínico creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @PostMapping
    public ResponseEntity<HistorialClinicoModel> save(@Valid @RequestBody HistorialClinicoModel historialClinicoModel){
        HistorialClinicoModel histGuardado = clinicoService.save(historialClinicoModel);
        this.agregarEnlaces(histGuardado);
        return ResponseEntity.status(HttpStatus.CREATED).body(histGuardado);
    }

    @Operation(summary = "Actualizar historial clínico", description = "Actualiza los datos de un historial clínico existente en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial clínico actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @PutMapping
    public ResponseEntity<HistorialClinicoModel> update(@Valid @RequestBody HistorialClinicoModel historialClinicoModel){
        HistorialClinicoModel histActualizado = clinicoService.save(historialClinicoModel);
        this.agregarEnlaces(histActualizado);
        return ResponseEntity.ok(histActualizado);
    }

    @Operation(summary = "Eliminar historial clínico (Modelo)", description = "Elimina un historial clínico del sistema enviando el modelo completo en la petición.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Historial clínico eliminado correctamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody HistorialClinicoModel historialClinicoModel){
        clinicoService.delete(historialClinicoModel);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Eliminar historial clínico por ID", description = "Elimina un historial clínico específico del sistema mediante su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Historial clínico eliminado correctamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        clinicoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Método auxiliar privado para reutilizar la lógica de inyección de enlaces hipermedia.
     * Limpia los enlaces previos antes de añadir los nuevos para evitar duplicados en operaciones secuenciales.
     */
    private void agregarEnlaces(HistorialClinicoModel hist) {
        hist.removeLinks(); // Previene enlaces duplicados si el objeto pasa por múltiples procesos

        hist.add(linkTo(methodOn(HistorialClinicoController.class).findById(hist.getId())).withSelfRel());
        hist.add(linkTo(methodOn(HistorialClinicoController.class).findAll()).withRel("todos-los-historiales"));
        hist.add(linkTo(methodOn(HistorialClinicoController.class).update(hist)).withRel("actualizar"));
        hist.add(linkTo(methodOn(HistorialClinicoController.class).deleteById(hist.getId())).withRel("eliminar-por-id"));
    }
}