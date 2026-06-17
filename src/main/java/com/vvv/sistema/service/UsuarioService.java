package com.vvv.sistema.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vvv.sistema.model.Usuario;
import com.vvv.sistema.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        return repository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return repository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario buscarPorCpf(String cpf) {
        return repository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado pelo CPF"));
    }

    public Usuario atualizar(Long id, Usuario dados) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(dados.getNome());
        usuario.setEndereco(dados.getEndereco());
        usuario.setCpf(dados.getCpf());
        usuario.setTelefone(dados.getTelefone());
        usuario.setProfissao(dados.getProfissao());
        usuario.setIdade(dados.getIdade());
        usuario.setEmail(dados.getEmail());
        usuario.setSenha(dados.getSenha());
        usuario.setTipo(dados.getTipo());

        return repository.save(usuario);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }

        repository.deleteById(id);
    }

    public Usuario login(String email, String senha) {
        return repository.findAll()
                .stream()
                .filter(u -> u.getEmail() != null
                        && u.getSenha() != null
                        && u.getEmail().equals(email)
                        && u.getSenha().equals(senha))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos"));
    }
}
