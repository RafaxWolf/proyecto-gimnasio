package com.proyectogimnasio.rutina.dto;

import com.proyectogimnasio.rutina.model.Ejercicio;
import com.proyectogimnasio.rutina.model.Rutina;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DetallesEjercicioRequest {
    private Ejercicio ejercicio;
    private Rutina rutina;


    @NotNull
    private Integer numeroEjercicios;
    @NotBlank
    private String duracionRutina;
    @NotBlank
    private String tiempoDescanso;
}
