package Hospital.facturacion.controller;

import Hospital.facturacion.model.Facturacion;
import Hospital.facturacion.security.jwt.JwtService;
import Hospital.facturacion.service.FacturacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(FacturacionController.class)
class FacturacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FacturacionService service;

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void findAll() throws Exception {
        Facturacion f = new Facturacion(1L, "Juan", "Pérez", "Consulta General", "45000", "2026-06-19");
        when(service.findAll()).thenReturn(List.of(f));

        mockMvc.perform(get("/api/v1/facturacion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].priNombre").value("Juan"));
    }

    @Test
    @WithMockUser
    void findById() throws Exception {
        Facturacion f = new Facturacion(1L, "Juan", "Pérez", "Consulta General", "45000", "2026-06-19");
        when(service.findById(1L)).thenReturn(Optional.of(f));

        mockMvc.perform(get("/api/v1/facturacion/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apPaterno").value("Pérez"));
    }

    @Test
    @WithMockUser
    void findByIdNoEncontrado() throws Exception {
        when(service.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/facturacion/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void save() throws Exception {
        Facturacion entrada = new Facturacion(0, "Ana", "Ruiz", "Pediatría", "30000", "2026-07-01");
        Facturacion guardada = new Facturacion(2L, "Ana", "Ruiz", "Pediatría", "30000", "2026-07-01");
        when(service.save(org.mockito.ArgumentMatchers.any(Facturacion.class))).thenReturn(guardada);

        mockMvc.perform(post("/api/v1/facturacion")
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.priNombre").value("Ana"));
    }

    @Test
    @WithMockUser
    void saveInvalido() throws Exception {
        Facturacion invalida = new Facturacion(); // campos @NotBlank vacios

        mockMvc.perform(post("/api/v1/facturacion")
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalida)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void update() throws Exception {
        Facturacion actualizada = new Facturacion(1L, "Juan", "Pérez", "Cardiología", "60000", "2026-07-01");
        when(service.save(org.mockito.ArgumentMatchers.any(Facturacion.class))).thenReturn(actualizada);

        mockMvc.perform(put("/api/v1/facturacion")
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(actualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoConsulta").value("Cardiología"));
    }

    @Test
    @WithMockUser
    void deleteById() throws Exception {
        mockMvc.perform(delete("/api/v1/facturacion/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}