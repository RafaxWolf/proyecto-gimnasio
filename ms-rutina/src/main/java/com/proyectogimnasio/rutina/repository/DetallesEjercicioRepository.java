package com.proyectogimnasio.rutina.repository;

import com.proyectogimnasio.rutina.model.DetallesEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallesEjercicioRepository extends JpaRepository<DetallesEjercicio, Long> {
}
