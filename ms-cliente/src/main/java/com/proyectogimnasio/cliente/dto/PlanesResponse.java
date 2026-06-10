package com.proyectogimnasio.cliente.dto;

import com.proyectogimnasio.planes.model.Pagos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanesResponse {
    private Long id;
    private String nombrePlan;
    private Double precioPlan;
    private String descripcionPlan;
    private String beneficios;
    private Pagos tipoPago;
}
