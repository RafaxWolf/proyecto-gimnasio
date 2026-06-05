package com.example.ms_entrenador.repository;


import com.example.ms_entrenador.model.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {


    boolean existsByRun(String run);

    Optional<Entrenador> findByRun(String run);
}
