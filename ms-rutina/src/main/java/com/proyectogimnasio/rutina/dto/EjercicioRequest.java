package com.proyectogimnasio.rutina.dto;

import com.proyectogimnasio.rutina.model.DetallesEjercicio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;
@Data
public class EjercicioRequest {
    @NotBlank(message = "Debe introducir un nombre al ejercicio")
    private String nombreEjercicio;
    @NotBlank(message = "Debe especificar que zona se ejercita")
    private String zonaEjercitada;
    @NotNull(message = "Debe incluir las repeticiones del ejercicio")
    private Integer repeticiones;

    private Set<DetallesEjercicio> detalles;
}
