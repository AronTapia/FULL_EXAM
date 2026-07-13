package Hospital.InventarioFarmacia.controller;

import Hospital.InventarioFarmacia.model.InventarioFarmacia;
import Hospital.InventarioFarmacia.service.InventarioFarmaciaService;
import Hospital.InventarioFarmacia.security.jwt.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(InventarioFarmaciaController.class)
class InventarioFarmaciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventarioFarmaciaService service;
    
    @MockitoBean
    private JwtService jwtService;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    @WithMockUser
    void findAll() throws Exception {
        InventarioFarmacia m = new InventarioFarmacia(
                1L, "Paracetamol 500mg", LocalDate.of(2027, 5, 15), 50, 5);
        when(service.findAll()).thenReturn(List.of(m));

        mockMvc.perform(get("/api/v1/inventarioFarmacia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreMedicamento").value("Paracetamol 500mg"));
    }

    @Test
    @WithMockUser
    void findById() throws Exception {
        InventarioFarmacia m = new InventarioFarmacia(
                1L, "Paracetamol 500mg", LocalDate.of(2027, 5, 15), 50, 5);
        when(service.findById(1L)).thenReturn(Optional.of(m));

        mockMvc.perform(get("/api/v1/inventarioFarmacia/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidadMedicamento").value(50));
    }

    @Test
    @WithMockUser
    void findByIdNoEncontrado() throws Exception {
        when(service.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/inventarioFarmacia/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void save() throws Exception {
        InventarioFarmacia entrada = new InventarioFarmacia(
                0, "Ibuprofeno 400mg", LocalDate.of(2026, 12, 1), 30, 8);
        InventarioFarmacia guardado = new InventarioFarmacia(
                2L, "Ibuprofeno 400mg", LocalDate.of(2026, 12, 1), 30, 8);
        when(service.save(org.mockito.ArgumentMatchers.any(InventarioFarmacia.class))).thenReturn(guardado);

        mockMvc.perform(post("/api/v1/inventarioFarmacia")
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreMedicamento").value("Ibuprofeno 400mg"));
    }

    @Test
    @WithMockUser
    void saveInvalido() throws Exception {
        InventarioFarmacia invalido = new InventarioFarmacia(); // nombreMedicamento vacio, precioMedicamento null

        mockMvc.perform(post("/api/v1/inventarioFarmacia")
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void update() throws Exception {
        InventarioFarmacia actualizado = new InventarioFarmacia(
                1L, "Paracetamol 500mg", LocalDate.of(2027, 5, 15), 40, 6);
        when(service.save(org.mockito.ArgumentMatchers.any(InventarioFarmacia.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/inventarioFarmacia")
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidadMedicamento").value(40));
    }

    @Test
    @WithMockUser
    void deleteById() throws Exception {
        mockMvc.perform(delete("/api/v1/inventarioFarmacia/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}