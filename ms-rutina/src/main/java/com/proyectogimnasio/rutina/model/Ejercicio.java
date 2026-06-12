package com.proyectogimnasio.rutina.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ejercicio")
public class Ejercicio{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ejercicio")
    private Long id;
    private String nombreEjercicio;
    private String zonaEjercitada;
    private Integer repeticiones;
    @OneToMany(mappedBy = "ejercicio")
    private Set<DetallesEjercicio> detalles;
}
