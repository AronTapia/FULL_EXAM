package Hospital.NotificacionPaciente.controller;

import Hospital.NotificacionPaciente.model.NotificacionPaciente;
import Hospital.NotificacionPaciente.service.NotificacionPacienteService;
import Hospital.NotificacionPaciente.security.jwt.JwtService;
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


@WebMvcTest(NotificacionPacienteController.class)
class NotificacionPacienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificacionPacienteService service;


    @MockitoBean
    private JwtService jwtService;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    @WithMockUser
    void findAll() throws Exception {
        NotificacionPaciente n = new NotificacionPaciente(
                1L, 101L, "12345678-9", "Juan Pérez", "Dr. Carlos Astudillo",
                LocalDate.of(2026, 5, 15), "Su receta médica ya está disponible para retiro.");
        when(service.findAll()).thenReturn(List.of(n));

        mockMvc.perform(get("/api/v1/notificacionPaciente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombrePaciente").value("Juan Pérez"));
    }

    @Test
    @WithMockUser
    void findById() throws Exception {
        NotificacionPaciente n = new NotificacionPaciente(
                1L, 101L, "12345678-9", "Juan Pérez", "Dr. Carlos Astudillo",
                LocalDate.of(2026, 5, 15), "Su receta médica ya está disponible para retiro.");
        when(service.findById(1L)).thenReturn(Optional.of(n));

        mockMvc.perform(get("/api/v1/notificacionPaciente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rutPaciente").value("12345678-9"));
    }

    @Test
    @WithMockUser
    void findByIdNoEncontrado() throws Exception {
        when(service.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/notificacionPaciente/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void save() throws Exception {
        NotificacionPaciente entrada = new NotificacionPaciente(
                0, 102L, "18765432-1", "María Jofre", "Dra. Ana María Silva",
                LocalDate.of(2026, 5, 16), "Recuerde asistir a su control anual de cardiología.");
        NotificacionPaciente guardada = new NotificacionPaciente(
                2L, 102L, "18765432-1", "María Jofre", "Dra. Ana María Silva",
                LocalDate.of(2026, 5, 16), "Recuerde asistir a su control anual de cardiología.");
        when(service.save(org.mockito.ArgumentMatchers.any(NotificacionPaciente.class))).thenReturn(guardada);

        mockMvc.perform(post("/api/v1/notificacionPaciente")
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombrePaciente").value("María Jofre"));
    }

    @Test
    @WithMockUser
    void saveInvalido() throws Exception {
        NotificacionPaciente invalida = new NotificacionPaciente(); // campos @NotBlank/@NotNull vacios

        mockMvc.perform(post("/api/v1/notificacionPaciente")
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalida)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void update() throws Exception {
        NotificacionPaciente actualizada = new NotificacionPaciente(
                1L, 101L, "12345678-9", "Juan Pérez", "Dr. Carlos Astudillo",
                LocalDate.of(2026, 5, 20), "Su receta fue actualizada.");
        when(service.save(org.mockito.ArgumentMatchers.any(NotificacionPaciente.class))).thenReturn(actualizada);

        mockMvc.perform(put("/api/v1/notificacionPaciente")
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(actualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Su receta fue actualizada."));
    }

    @Test
    @WithMockUser
    void deleteById() throws Exception {
        mockMvc.perform(delete("/api/v1/notificacionPaciente/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}
