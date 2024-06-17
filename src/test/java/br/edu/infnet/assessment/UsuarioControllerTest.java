package br.edu.infnet.assessment;

import br.edu.infnet.assessment.controller.UsuarioController;
import br.edu.infnet.assessment.model.Usuario;
import br.edu.infnet.assessment.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSalvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId("1");
        usuario.setNome("João");
        usuario.setSenha("senha123");
        usuario.setPapel("USER");

        Mockito.when(usuarioService.saveUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);

        ResultActions result = mockMvc.perform(post("/api/public/usuarios")
                .with(user("user").password("password").roles("USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)));

        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.senha").value("senha123"))
                .andExpect(jsonPath("$.papel").value("USER"));
    }

    @Test
    public void testBuscarUsuarioPorId() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId("1");
        usuario.setNome("João");
        usuario.setSenha("senha123");
        usuario.setPapel("USER");

        Mockito.when(usuarioService.getUsuarioById("1")).thenReturn(Optional.of(usuario));

        ResultActions result = mockMvc.perform(get("/api/public/usuarios/{id}", "1")
                .with(user("user").password("password").roles("USER"))
                .with(csrf()));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.senha").value("senha123"))
                .andExpect(jsonPath("$.papel").value("USER"));
    }

    @Test
    public void testAtualizarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId("1");
        usuario.setNome("João");
        usuario.setSenha("senha123");
        usuario.setPapel("USER");

        Mockito.when(usuarioService.updateUsuario(eq("1"), Mockito.any(Usuario.class))).thenReturn(usuario);

        ResultActions result = mockMvc.perform(put("/api/public/usuarios/{id}", "1")
                .with(user("user").password("password").roles("USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.senha").value("senha123"))
                .andExpect(jsonPath("$.papel").value("USER"));
    }

    @Test
    public void testDeletarUsuario() throws Exception {
        Mockito.doNothing().when(usuarioService).deleteUsuario("1");

        ResultActions result = mockMvc.perform(delete("/api/public/usuarios/{id}", "1")
                .with(user("user").password("password").roles("USER"))
                .with(csrf()));

        result.andExpect(status().isNoContent());
    }
}
