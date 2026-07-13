package Hospital.facturacion.controller;

import Hospital.facturacion.model.Facturacion;
import Hospital.facturacion.service.FacturacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/facturacion")
@RequiredArgsConstructor
public class FacturacionController {

    private final FacturacionService service;

    @Operation(
            summary = "Listar todas las facturaciones",
            description = "Retorna una lista con todos los registros de facturación del sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<Facturacion>> findAll(){
        List<Facturacion> lista = service.findAll();
        for (Facturacion f : lista) {
            f.add(linkTo(methodOn(FacturacionController.class).findById(f.getId())).withSelfRel());
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(
            summary = "Buscar facturación por ID",
            description = "Busca y retorna un registro de facturación específico mediante su identificador único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facturación encontrada"),
            @ApiResponse(responseCode = "404", description = "Facturación no encontrada")
    })
    @GetMapping("{id}")
    public ResponseEntity<Facturacion> findById(@PathVariable Long id){
        return service.findById(id)
                .map(facturacion -> {
                    facturacion.add(linkTo(methodOn(FacturacionController.class).findAll()).withRel("todos"));
                    facturacion.add(linkTo(methodOn(FacturacionController.class).findById(facturacion.getId())).withSelfRel());
                    facturacion.add(linkTo(methodOn(FacturacionController.class).update(facturacion)).withRel("update"));
                    facturacion.add(linkTo(methodOn(FacturacionController.class).deleteById(facturacion.getId())).withRel("delete"));
                    return ResponseEntity.ok(facturacion);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear facturación",
            description = "Registra una nueva facturación médica en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Facturación creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<Facturacion> save(@Valid @RequestBody Facturacion facturacion){
        Facturacion fac = service.save(facturacion);
        fac.add(linkTo(methodOn(FacturacionController.class).findAll()).withRel("todos"));
        fac.add(linkTo(methodOn(FacturacionController.class).findById(fac.getId())).withSelfRel());
        fac.add(linkTo(methodOn(FacturacionController.class).update(fac)).withRel("update"));
        fac.add(linkTo(methodOn(FacturacionController.class).deleteById(fac.getId())).withRel("delete"));
        return ResponseEntity.status(HttpStatus.CREATED).body(fac);
    }

    @Operation(
            summary = "Actualizar facturación",
            description = "Actualiza los datos de un registro de facturación existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facturación actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos")
    })
    @PutMapping
    public ResponseEntity<Facturacion> update(@RequestBody Facturacion facturacion){
        Facturacion fac = service.save(facturacion);
        fac.add(linkTo(methodOn(FacturacionController.class).findAll()).withRel("todos"));
        fac.add(linkTo(methodOn(FacturacionController.class).findById(fac.getId())).withSelfRel());
        fac.add(linkTo(methodOn(FacturacionController.class).deleteById(fac.getId())).withRel("delete"));
        return ResponseEntity.ok(fac);
    }

    @Operation(
            summary = "Eliminar facturación por objeto",
            description = "Elimina un registro de facturación enviando el objeto completo en el cuerpo de la petición."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Facturación eliminada correctamente")
    })
    @DeleteMapping
    public ResponseEntity delete(@RequestBody Facturacion facturacion){
        service.delete(facturacion);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Eliminar facturación por ID",
            description = "Elimina un registro de facturación específico usando su identificador en la URL."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Facturación eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Facturación no encontrada")
    })
    @DeleteMapping("{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}