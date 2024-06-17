package br.edu.infnet.assessment;

import br.edu.infnet.assessment.controller.DepartamentoController;
import br.edu.infnet.assessment.model.Departamento;
import br.edu.infnet.assessment.service.DepartamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(DepartamentoController.class)
public class DepartamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartamentoService departamentoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateDepartamento() throws Exception {
        Departamento departamento = new Departamento("Logistica", "CD Cajamar/SP");

        Mockito.when(departamentoService.saveDepartamento(any(Departamento.class))).thenReturn(departamento);

        mockMvc.perform(post("/api/public/departamentos")
                        .with(user("user").password("password").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departamento)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetDepartamentoById() throws Exception {
        Departamento departamento = new Departamento("Logistica", "CD Cajamar/SP");

        Mockito.when(departamentoService.getDepartamentoById(1L)).thenReturn(Optional.of(departamento));

        mockMvc.perform(get("/api/public/departamentos/{id}", "1")
                .with(user("user").password("password").roles("USER"))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Logistica"))
                .andExpect(jsonPath("$.local").value("CD Cajamar/SP"));
    }

    @Test
    public void testUpdateDepartamento() throws Exception {
        Departamento departamento = new Departamento("Logistica", "CD Cajamar/SP");

        Mockito.when(departamentoService.updateDepartamento(eq(1L), any(Departamento.class))).thenReturn(departamento);

        mockMvc.perform(put("/api/public/departamentos/{id}", "1")
                        .with(user("user").password("password").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departamento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Logistica"))
                .andExpect(jsonPath("$.local").value("CD Cajamar/SP"));
    }

    @Test
    public void testDeleteDepartamento() throws Exception {
        Mockito.doNothing().when(departamentoService).deleteDepartamento(1L);

        mockMvc.perform(delete("/api/public/departamentos/{id}", "1")
                        .with(user("user").password("password").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}
