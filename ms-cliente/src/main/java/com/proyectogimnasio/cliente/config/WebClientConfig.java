package com.proyectogimnasio.cliente.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced // <-- ESTO ES CLAVE: Le enseña a WebClient a usar los nombres de Eureka (ms-planes)
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .filter((request, next) -> {
                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                    if (attributes != null) {
                        HttpServletRequest currentRequest = attributes.getRequest();
                        String authHeader = currentRequest.getHeader(HttpHeaders.AUTHORIZATION);

                        if (authHeader != null && !authHeader.isEmpty()) {
                            ClientRequest filteredRequest = ClientRequest.from(request)
                                    .header(HttpHeaders.AUTHORIZATION, authHeader)
                                    .build();
                            return next.exchange(filteredRequest);
                        }
                    }
                    return next.exchange(request);
                });
    }
}