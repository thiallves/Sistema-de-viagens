package com.vvv.sistema.service;

import com.vvv.sistema.model.Usuario;
import com.vvv.sistema.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    // ✅ cadastrar usuário (SEM CRIPTOGRAFIA)
    public Usuario cadastrarUsuario(Usuario usuario) {
        return repository.save(usuario);
    }

    // ✅ listar
    public List<Usuario> listarUsuarios() {
        return repository.findAll();
    }

    // ✅ login simples
    public Usuario login(String email, String senha) {

        Usuario usuario = repository.findAll()
                .stream()
                .filter(u -> u.getEmail() != null
                        && u.getSenha() != null
                        && u.getEmail().equals(email)
                        && u.getSenha().equals(senha))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos"));

        return usuario;
    }
}