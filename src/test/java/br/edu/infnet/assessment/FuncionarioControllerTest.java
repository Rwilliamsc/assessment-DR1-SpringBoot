package br.edu.infnet.assessment;

import br.edu.infnet.assessment.controller.FuncionarioController;
import br.edu.infnet.assessment.model.Funcionario;
import br.edu.infnet.assessment.service.FuncionarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FuncionarioController.class)
public class FuncionarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FuncionarioService funcionarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateFuncionario() throws Exception {
        Funcionario funcionario = new Funcionario("João Silva", "Rua das Flores, 123", "(11) 1234-5678", "joao.silva@example.com", new Date(), null);

        Mockito.when(funcionarioService.saveFuncionario(any(Funcionario.class))).thenReturn(funcionario);

        mockMvc.perform(post("/api/public/funcionarios")
                        .with(user("user").password("password").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetFuncionarioById() throws Exception {
        Funcionario funcionario = new Funcionario("João Silva", "Rua das Flores, 123", "(11) 1234-5678", "joao.silva@example.com", new Date(), null);

        Mockito.when(funcionarioService.getFuncionarioById(eq(1L))).thenReturn(Optional.of(funcionario));

        mockMvc.perform(get("/api/public/funcionarios/{id}", "1")
                        .with(user("user").password("password").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.endereco").value("Rua das Flores, 123"));
    }

    @Test
    public void testUpdateFuncionario() throws Exception {
        Funcionario funcionario = new Funcionario("João Silva", "Rua das Flores, 123", "(11) 1234-5678", "joao.silva@example.com", new Date(), null);

        Mockito.when(funcionarioService.updateFuncionario(eq(1L), any(Funcionario.class))).thenReturn(funcionario);

        mockMvc.perform(put("/api/public/funcionarios/{id}", "1")
                        .with(user("user").password("password").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.endereco").value("Rua das Flores, 123"));
    }

    @Test
    public void testDeleteFuncionario() throws Exception {
        Mockito.doNothing().when(funcionarioService).deleteFuncionario(eq(1L));

        mockMvc.perform(delete("/api/public/funcionarios/{id}", "1")
                        .with(user("user").password("password").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}