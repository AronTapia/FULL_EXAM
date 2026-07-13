package Hospital.InventarioFarmacia.controller;

import Hospital.InventarioFarmacia.model.InventarioFarmacia;
import Hospital.InventarioFarmacia.service.InventarioFarmaciaService;
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
@RequestMapping("/api/v1/inventarioFarmacia")
@RequiredArgsConstructor
@Tag(name = "Inventario de Farmacia", description = "Controlador para la gestión de stock y medicamentos en la farmacia")
public class InventarioFarmaciaController {

    private final InventarioFarmaciaService service;

    @Operation(
            summary = "Obtener todo el inventario",
            description = "Retorna una lista con todos los medicamentos registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping
    public ResponseEntity<List<InventarioFarmacia>> findAll(){
        List<InventarioFarmacia> lista = service.findAll();
        for (InventarioFarmacia elemento : lista) {
            elemento.add(linkTo(methodOn(InventarioFarmaciaController.class).findById(elemento.getId())).withSelfRel());
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(
            summary = "Buscar medicamento por ID",
            description = "Busca y retorna un único registro de inventario basado en su identificador numérico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Medicamento no encontrado")
    })
    @GetMapping("{id}")
    public ResponseEntity<InventarioFarmacia> findById(@PathVariable Long id){
        return service.findById(id)
                .map(elemento -> {
                    elemento.add(linkTo(methodOn(InventarioFarmaciaController.class).findAll()).withRel("todos"));
                    elemento.add(linkTo(methodOn(InventarioFarmaciaController.class).findById(elemento.getId())).withSelfRel());
                    elemento.add(linkTo(methodOn(InventarioFarmaciaController.class).update(elemento)).withRel("update"));
                    elemento.add(linkTo(methodOn(InventarioFarmaciaController.class).deleteById(elemento.getId())).withRel("delete"));
                    return ResponseEntity.ok(elemento);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear medicamento en inventario",
            description = "Registra un nuevo medicamento y su stock inicial en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medicamento creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<InventarioFarmacia> save(@Valid @RequestBody InventarioFarmacia inventarioFarmacia){
        InventarioFarmacia elemento = service.save(inventarioFarmacia);
        elemento.add(linkTo(methodOn(InventarioFarmaciaController.class).findAll()).withRel("todos"));
        elemento.add(linkTo(methodOn(InventarioFarmaciaController.class).findById(elemento.getId())).withSelfRel());
        elemento.add(linkTo(methodOn(InventarioFarmaciaController.class).update(elemento)).withRel("update"));
        elemento.add(linkTo(methodOn(InventarioFarmaciaController.class).deleteById(elemento.getId())).withRel("delete"));
        return ResponseEntity.status(HttpStatus.CREATED).body(elemento);
    }

    @Operation(
            summary = "Actualizar medicamento",
            description = "Modifica los datos existentes de un medicamento enviado en el cuerpo de la petición."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamento actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos"),
            @ApiResponse(responseCode = "404", description = "Medicamento no encontrado")
    })
    @PutMapping
    public ResponseEntity<InventarioFarmacia> update(@RequestBody InventarioFarmacia inventarioFarmacia){
        InventarioFarmacia elemento = service.save(inventarioFarmacia);
        elemento.add(linkTo(methodOn(InventarioFarmaciaController.class).findAll()).withRel("todos"));
        elemento.add(linkTo(methodOn(InventarioFarmaciaController.class).findById(elemento.getId())).withSelfRel());
        elemento.add(linkTo(methodOn(InventarioFarmaciaController.class).deleteById(elemento.getId())).withRel("delete"));
        return ResponseEntity.ok(elemento);
    }

    @Operation(
            summary = "Eliminar medicamento por objeto",
            description = "Elimina un registro de inventario pasando el objeto completo en el cuerpo."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Medicamento eliminado con éxito")
    })
    @DeleteMapping
    public ResponseEntity delete(@RequestBody InventarioFarmacia inventarioFarmacia){
        service.delete(inventarioFarmacia);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Eliminar medicamento por ID",
            description = "Elimina de forma permanente un registro del inventario utilizando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Medicamento eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "ID no encontrado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
