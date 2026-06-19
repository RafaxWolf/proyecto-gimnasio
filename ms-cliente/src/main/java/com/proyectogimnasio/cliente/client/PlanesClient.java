package com.proyectogimnasio.cliente.client;

import com.proyectogimnasio.cliente.dto.ApiResponse;
import com.proyectogimnasio.cliente.dto.PlanesResponse;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class PlanesClient {
    private final WebClient webClient;

    public PlanesClient(WebClient.Builder webClientBuilder, @Value("${planes.service.url:http://ms-planes}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public PlanesResponse getPlan(Long id) {
        try {
            ApiResponse<PlanesResponse> response = webClient.get()
                    .uri("/api/v3/planes/" + id)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<PlanesResponse>>() {})
                    .block();
            return response != null ? response.getData() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public Object activarSuscripcion(Object suscripcionRequest) {
        try {
            return webClient.post()
                    .uri("/api/v3/suscripciones")
                    .bodyValue(suscripcionRequest)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }

    public Object getSuscripcionPorCliente(Long idCliente) {
        try {
            return webClient.get()
                    .uri("/api/v3/suscripciones/cliente/" + idCliente)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }
}