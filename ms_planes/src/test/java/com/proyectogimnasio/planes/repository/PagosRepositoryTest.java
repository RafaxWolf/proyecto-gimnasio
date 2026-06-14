package com.proyectogimnasio.planes.repository;

import com.proyectogimnasio.planes.model.Pagos;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class PagosRepositoryTest {

    @Autowired
    private PagosRepository pagosRepository;

    @Test
    public void debeGuardarMetodoDePagoExitosamente() {
        // Arrange
        Pagos pago = new Pagos();
        pago.setTipoPago("Debito");
        pago.setNumTarjeta("9876543210123456");
        pago.setFechaVencimiento("12/28");
        pago.setCvc(567);
        pago.setDireccionFacturacion("Av. Siempreviva 742");
        pago.setCodigoPostal("12345");
        pago.setIdCliente(100L);

        // Act
        Pagos guardado = pagosRepository.save(pago);

        // Assert
        assertNotNull(guardado.getId());
        assertEquals("Debito", guardado.getTipoPago());
        assertEquals(567, guardado.getCvc());
    }

    @Test
    public void debeBuscarPagoPorId() {
        Pagos pago = new Pagos(null, "Efectivo", null, null, null, null, null, 200L);
        Pagos guardado = pagosRepository.save(pago);

        Optional<Pagos> encontrado = pagosRepository.findById(guardado.getId());

        assertTrue(encontrado.isPresent());
        assertEquals(200L, encontrado.get().getIdCliente());
    }
}