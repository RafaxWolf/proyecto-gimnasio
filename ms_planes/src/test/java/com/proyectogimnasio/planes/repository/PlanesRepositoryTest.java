package com.proyectogimnasio.planes.repository;

import com.proyectogimnasio.planes.model.Pagos;
import com.proyectogimnasio.planes.model.Planes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class PlanesRepositoryTest {

    @Autowired
    private PlanesRepository planesRepository;

    @Autowired
    private PagosRepository pagosRepository;

    @Test
    void debeGuardarPlanConMetodoDePago() {
        // Arrange
        Pagos pago = new Pagos();
        pago.setTipoPago("Tarjeta de Credito");
        pago.setNumTarjeta(1234567890123456.0);
        Pagos pagoGuardado = pagosRepository.save(pago);

        Planes plan = new Planes(null, "Plan Premium", 45000.0, "Acceso total", "Gimnasio + Piscina", pagoGuardado);

        // Act
        Planes guardado = planesRepository.save(plan);

        // Assert
        assertNotNull(guardado.getId());
        assertEquals("Plan Premium", guardado.getNombrePlan());
        assertEquals(45000.0, guardado.getPrecioPlan());
        assertNotNull(guardado.getTipoPago());
        assertEquals("Tarjeta de Credito", guardado.getTipoPago().getTipoPago());
    }

    @Test
    void debeBuscarPlanPorId() {
        // Arrange
        Planes plan = new Planes(null, "Plan Estudiante", 25000.0, "Lunes a Viernes", "Ninguno", null);
        Planes guardado = planesRepository.save(plan);

        // Act
        Optional<Planes> resultado = planesRepository.findById(guardado.getId());

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(guardado.getId(), resultado.get().getId());
        assertEquals("Plan Estudiante", resultado.get().getNombrePlan());
        assertEquals(25000.0, resultado.get().getPrecioPlan());
    }

    @Test
    void debeListarPlanes() {
        // Arrange
        planesRepository.save(new Planes(null, "Plan Diario", 5000.0, "Solo 1 dia", "Lockers", null));
        planesRepository.save(new Planes(null, "Plan Mensual", 30000.0, "Mes completo", "Lockers + Toalla", null));

        // Act
        List<Planes> resultado = planesRepository.findAll();

        // Assert
        assertFalse(resultado.isEmpty());
        assertTrue(resultado.size() >= 2);
    }

    @Test
    void debeEliminarPlan() {
        // Arrange
        Planes plan = new Planes(null, "Plan Temporal", 10000.0, "Por vencer", "Ninguno", null);
        Planes guardado = planesRepository.save(plan);

        // Act
        planesRepository.deleteById(guardado.getId());

        // Assert
        Optional<Planes> resultado = planesRepository.findById(guardado.getId());
        assertFalse(resultado.isPresent());
    }
}