package com.proyectogimnasio.rutina.repository;

import com.proyectogimnasio.rutina.model.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio,Long> {
}
