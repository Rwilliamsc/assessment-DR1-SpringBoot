package br.edu.infnet.assessment.service;

import br.edu.infnet.assessment.model.Usuario;
import br.edu.infnet.assessment.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(String id) {
        return usuarioRepository.findById(id);
    }

    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(String id, Usuario usuarioDetails) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado com id: " + id));

        usuario.setNome(usuarioDetails.getNome());
        usuario.setSenha(usuarioDetails.getSenha());
        usuario.setPapel(usuarioDetails.getPapel());

        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(String id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario não encontrado com id: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
