package com.proyectogimnasio.cliente.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClienteRequest {
    @NotBlank(message = "El nombre no debe quedar en blanco")
    private String nombres;
    @NotBlank(message = "El apellido no debe de quedar en blanco")
    private String apellidos;
    @NotBlank(message = "El run es obligatorio")
    private String run;
    @NotNull(message = "El correo es obligatorio")
    private String correo;
    @NotNull(message = "Debe ingresar el id del plan")
    private Long idPlan;
}
