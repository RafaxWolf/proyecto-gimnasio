package com.proyectogimnasio.cliente.service;

import com.proyectogimnasio.cliente.client.PlanesClient;
import com.proyectogimnasio.cliente.dto.ClienteRequest;
import com.proyectogimnasio.cliente.dto.ClienteResponse;
import com.proyectogimnasio.cliente.model.Cliente;
import com.proyectogimnasio.cliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {
    private final ClienteRepository repo;
    private final PlanesClient client;


//mapToResponse sirve para que todo quede ordenado o mapeado para que cuando se retorne, muestre los datos del cliente
    private ClienteResponse mapToResponse(Cliente c, String token) {
        log.info("Mapeando cliente",
                keyValue("idCliente", c.getId())
        );
        var plan1 = client.getPlan(c.getIdPlan(), token);


        return ClienteResponse.builder().id(c.getId()).nombres(c.getNombres()).
                apellidos(c.getApellidos()).
                run(c.getRun()).
                correo(c.getCorreo()).
                idPlan(c.getIdPlan()).
                build();

    }
}
