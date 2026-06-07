package com.example.ms_entrenador.controller;


import com.example.ms_entrenador.dto.ApiResponse;
import com.example.ms_entrenador.dto.EntrenadorRequest;
import com.example.ms_entrenador.dto.EntrenadorResponse;
import com.example.ms_entrenador.service.EntrenadorService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(
        name = "Entrenadores",
        description = "Endpoints para gestion de Entrenadores"
)
@RestController
@RequestMapping("/api/v1/entrenadores")
@RequiredArgsConstructor
public class EntrenadorController {

    private final EntrenadorService service;

    @Operation(
            summary = "anadir entrenador",
            description = "Recibe los datos del entrenador, valida el cumplimiento de las reglas de negocio y realiza la creación."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Entrenador anadido correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validación fallida "),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token invalido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "No tienes permisos suficientes"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "El entrenador ya se encuentra registrado con ese RUN")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EntrenadorResponse>> add(@Valid @RequestBody EntrenadorRequest e){

        return ResponseEntity.status(201).body(
            ApiResponse.<EntrenadorResponse>builder().success(true)
                    .message("Entrenador creado")
                    .data(service.add(e)).build()

        );

    }
    @Operation(
            summary = "encontrar entrenador proporcionando su id ",
            description = "permite retornar al entrenador buscado si existe "
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Entrenador encontrado "),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token invalido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "No tienes permisos suficientes"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Entrenador no encontrado ")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EntrenadorResponse>> findById(
                    @Parameter(description = "id del entrenador a consultar", example = "1", required = true)
                    @PathVariable Long id){

        return ResponseEntity.status(200).body(
                ApiResponse.<EntrenadorResponse>builder().success(true).message("Encontrado")
                        .data(service.findById(id)).build()
        );
    }
    @Operation(
            summary = "obtener a todos los entrenadores registrados ",
            description = "permite retornar una lista de todos los entrenadores que se encuentran registrados "
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado de entrenadores obtenido "),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token invalido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "No tienes permisos suficientes")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<EntrenadorResponse>>> getAll(){

        return ResponseEntity.status(200).body(
                ApiResponse.<List<EntrenadorResponse>>builder().success(true)
                        .data(service.getAll()).build()
        );

    }
    @Operation(
            summary = "actualizar a un entrenador",
            description = "permite actualizar datos de un entrenador mediante su id "
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Entrenador actualizado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de actualización invalidos"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token invalido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acceso denegado. Se requiere rol ADMIN"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Entrenador a modificar no encontrado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EntrenadorResponse>> update(
            @Parameter(description = "id del entrenador que se desea modificar", example = "1", required = true)
            @PathVariable Long id, @Valid @RequestBody EntrenadorRequest e) {

        return ResponseEntity.ok(

                ApiResponse.<EntrenadorResponse>builder().success(true)
                        .data(service.update(id,e)).build()

        );

    }
    @Operation(
            summary = "eliminar a un entrenador registrado",
            description = "permite eliminar a un entrenado mediante su id "
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Entrenador eliminado "),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token invalido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "No tienes permisos suficientes"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Entrenador a eliminar no encontrado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "id del entrenador a eliminar ", example = "1", required = true)
            @PathVariable Long id){
        service.delete(id);
        return ResponseEntity.ok(

                ApiResponse.<Void>builder().success(true).message("Entrenador Eliminado").build()
        );
    }
    @Operation(
            summary = "encontrar entrenador proporcionando su run",
            description = "si el entrenador buscado existe retornara sus datos "
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Entrenador buscado por run encontrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autenticado o token invalido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "No tienes permisos suficientes"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "No se encontro al entrenador por ese nombre")
    })
    @GetMapping("/buscar-por-run/{run}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EntrenadorResponse>> buscarPorRun(
            @Parameter(description = "Run del entrenador a buscar", example = "111-1", required = true)
            @PathVariable String run){

        return ResponseEntity.ok(ApiResponse.<EntrenadorResponse>builder()
                .success(true)
                .data(service.buscarPorRun(run))
                .build()
        );
    }

}
