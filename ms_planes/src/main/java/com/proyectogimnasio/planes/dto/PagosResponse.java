package com.proyectogimnasio.planes.dto;

import com.proyectogimnasio.cliente.model.Cliente;



import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagosResponse {
    private Long idPago;
    private String tipoPago;
    private Double numTarjeta;
    private String fechaVencimiento;
    private Integer cvc;
    private String direccionFacturacion;
    private String codigoPostal;
    private Cliente cliente;
}
