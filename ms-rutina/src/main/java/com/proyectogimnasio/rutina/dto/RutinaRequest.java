package com.proyectogimnasio.rutina.dto;

import com.proyectogimnasio.rutina.model.DetallesEjercicio;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;
@Data
public class RutinaRequest {
    @NotBlank(message = "El nombre de la rutina es obligatorio")
    private String nombreRutina;
    @NotBlank(message = "Debe introducir una descripcion a la rutina")
    private String descripcionRutina;
    private Set<DetallesEjercicio> detalles;
}
