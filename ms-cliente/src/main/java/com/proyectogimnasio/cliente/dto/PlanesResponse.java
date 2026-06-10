package com.proyectogimnasio.cliente.dto;

import com.example.ms_planes.model.Pagos;

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
    private Integer precioPlan;
    private Pagos Pagos;
}
