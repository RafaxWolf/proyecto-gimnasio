package com.proyectogimnasio.planes.service;

import com.proyectogimnasio.planes.client.ClienteClient;
import com.proyectogimnasio.planes.dto.*;
import com.proyectogimnasio.planes.model.Pagos;
import com.proyectogimnasio.planes.model.Planes;
import com.proyectogimnasio.planes.repository.PagosRepository;
import com.proyectogimnasio.planes.repository.PlanesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlanesServiceTest {

    @Mock
    private PlanesRepository planesRepository;

    @Mock
    private PagosRepository pagosRepository;

    @Mock
    private ClienteClient client;

    @InjectMocks
    private PlanesService planesService;

    private final String TOKEN_MOCK = "Bearer token-valido-123";

    @Test
    void deberiaCrearPlanCorrectamente() {
        // Arrange
        Pagos pagoMock = new Pagos(); // Creamos un mock de la relación
        pagoMock.setId(1L);

        PlanesRequest request = new PlanesRequest();
        request.setNombrePlan("Plan Estandar");
        request.setPrecioPlan(2700.0);
        request.setDescripcionPlan("Acceso libre");
        request.setBeneficios("Estacionamiento gratis");
        request.setTipoPago(pagoMock);

        // Usamos el constructor @AllArgsConstructor generado por Lombok en Planes
        Planes planGuardado = new Planes(1L, "Plan Estandar", 2700.0, "Acceso libre", "Estacionamiento gratis", pagoMock);

        when(planesRepository.save(any(Planes.class))).thenReturn(planGuardado);

        // Act
        PlanesResponse resultado = planesService.addPlan(request, TOKEN_MOCK);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Plan Estandar", resultado.getNombrePlan());
        assertEquals(2700.0, resultado.getPrecioPlan());
        verify(planesRepository).save(any(Planes.class));
    }

    @Test
    void deberiaRetornarPlanCuandoExiste() {
        // Arrange
        Pagos pagoMock = new Pagos();
        Planes plan = new Planes(1L, "Plan Premium", 5000.0, "Premium", "Todos", pagoMock);

        when(planesRepository.findById(1L)).thenReturn(Optional.of(plan));

        // Act
        PlanesResponse resultado = planesService.findByIdPlan(1L, TOKEN_MOCK);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Plan Premium", resultado.getNombrePlan());
        verify(planesRepository).findById(1L);
    }

    @Test
    void deberiaLanzarExcepcionCuandoPlanNoExiste() {
        // Arrange
        when(planesRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> planesService.findByIdPlan(99L, TOKEN_MOCK)
        );

        assertEquals("Plan no Existe", exception.getMessage());
        verify(planesRepository).findById(99L);
    }

    @Test
    void deberiaRetornarListaDePlanes() {
        // Arrange
        Planes plan = new Planes(1L, "Plan Estandar", 2700.0, "Acceso libre", "Ninguno", null);
        when(planesRepository.findAll()).thenReturn(List.of(plan));

        // Act
        List<PlanesResponse> resultado = planesService.getAllPlanes(TOKEN_MOCK);

        // Assert
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Plan Estandar", resultado.get(0).getNombrePlan());
        verify(planesRepository).findAll();
    }

    @Test
    void deberiaActualizarPlanCorrectamente() {
        // Arrange
        Planes planExistente = new Planes(1L, "Plan Antiguo", 2000.0, "Desc", "Ben", null);

        PlanesRequest request = new PlanesRequest();
        request.setNombrePlan("Plan Actualizado");
        request.setPrecioPlan(3000.0);
        request.setDescripcionPlan("Nueva desc");
        request.setBeneficios("Nuevos ben");
        request.setTipoPago(null);

        when(planesRepository.findById(1L)).thenReturn(Optional.of(planExistente));
        when(planesRepository.save(any(Planes.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        PlanesResponse resultado = planesService.updatePlan(1L, request, TOKEN_MOCK);

        // Assert
        assertNotNull(resultado);
        assertEquals("Plan Actualizado", resultado.getNombrePlan());
        assertEquals(3000.0, resultado.getPrecioPlan());
        verify(planesRepository).findById(1L);
        verify(planesRepository).save(any(Planes.class));
    }


    @Test
    void deberiaEliminarPagoCorrectamente() {
        // Arrange
        when(pagosRepository.existsById(1L)).thenReturn(true);
        doNothing().when(pagosRepository).deleteById(1L);

        // Act
        planesService.deletePago(1L);

        // Assert
        verify(pagosRepository).existsById(1L);
        verify(pagosRepository).deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarPagoInexistente() {
        // Arrange
        when(pagosRepository.existsById(99L)).thenReturn(false);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> planesService.deletePago(99L)
        );

        assertEquals("No se puede eliminar un metodo de pago inexistente", exception.getMessage());
        verify(pagosRepository).existsById(99L);
        verify(pagosRepository, never()).deleteById(anyLong());
    }
}