package com.proyectogimnasio.planes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.proyectogimnasio.planes.dto.PlanesRequest;
import com.proyectogimnasio.planes.dto.PlanesResponse;
import com.proyectogimnasio.planes.security.JwtUtil;
import com.proyectogimnasio.planes.service.PlanesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlanesController.class)
@AutoConfigureMockMvc
class PlanesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockitoBean
    private PlanesService service;

    @MockitoBean
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @WithMockUser(roles = "USER")
    void debeListarPlan() throws Exception {
        // Arrange
        PlanesResponse plan = new PlanesResponse(1L, "estandar", 2700.0, "Hola", "puede entrenar", null);
        List<PlanesResponse> planes = List.of(plan);

        when(service.getAllPlanes(anyString())).thenReturn(planes);

        // Act & Assert
        mockMvc.perform(get("/api/v3/planes")
                        .header("Authorization", "Bearer token-valido"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].nombrePlan").value("estandar"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void debeCrearPlan() throws Exception {
        // Arrange
        PlanesRequest dto = new PlanesRequest();
        dto.setNombrePlan("estandar");
        dto.setPrecioPlan(2700.0);
        dto.setDescripcionPlan("Hola");
        dto.setBeneficios("puede entrenar");
        dto.setTipoPago(null);

        PlanesResponse creado = new PlanesResponse(1L, "estandar", 2700.0, "Hola", "puede entrenar", null);
        when(service.addPlan(any(PlanesRequest.class), anyString())).thenReturn(creado);

        // Act & Assert
        mockMvc.perform(post("/api/v3/planes")
                        .header("Authorization", "Bearer token-valido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Plan añadido"))
                .andExpect(jsonPath("$.data.nombrePlan").value("estandar"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void debeActualizarPlan() throws Exception {
        // Arrange
        PlanesRequest dto = new PlanesRequest();
        dto.setNombrePlan("Plan nuevo"); // Cambiado para que coincida con la semántica del test
        dto.setPrecioPlan(3000.0);
        dto.setDescripcionPlan("Hola");
        dto.setBeneficios("puede entrenar");
        dto.setTipoPago(null);

        // Corregido: El objeto de retorno debe reflejar el cambio ("Plan nuevo") para que el assert no falle
        PlanesResponse actualizado = new PlanesResponse(1L, "Plan nuevo", 3000.0, "Hola", "puede entrenar", null);

        when(service.updatePlan(eq(1L), any(PlanesRequest.class), anyString())).thenReturn(actualizado);

        // Act & Assert
        mockMvc.perform(put("/api/v3/planes/1")
                        .header("Authorization", "Bearer token-valido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Plan Actualizado"))
                .andExpect(jsonPath("$.data.nombrePlan").value("Plan nuevo")); // ¡Ahora sí coincide!
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void debeEliminarPlan() throws Exception {
        // Arrange
        doNothing().when(service).deletePlan(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/v3/planes/1")
                        .header("Authorization", "Bearer token-valido")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Plan eliminado"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void debeObtenerPlanConHateoas() throws Exception {
        // Arrange
        PlanesResponse planMock = new PlanesResponse();
        planMock.setId(1L);
        planMock.setNombrePlan("Premium");
        planMock.setPrecioPlan(5000.0);
        planMock.setDescripcionPlan("Acceso total");
        planMock.setBeneficios("Todos");
        planMock.setTipoPago(null);

        when(service.findByIdPlan(anyLong(), anyString())).thenReturn(planMock);

        // Act & Assert
        mockMvc.perform(get("/api/v3/planes/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token-valido")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Plan obtenido"))
                // Los siguientes asserts asumen que tu controlador inyecta la propiedad 'links' (HATEOAS de Spring) dentro de 'data' o directamente en el wrapper.
                .andExpect(jsonPath("$.data.links[?(@.rel=='self')].href").exists())
                .andExpect(jsonPath("$.data.links[?(@.rel=='all')].href").exists())
                .andExpect(jsonPath("$.data.links[?(@.rel=='update')].href").exists())
                .andExpect(jsonPath("$.data.links[?(@.rel=='delete')].href").exists());
    }
}