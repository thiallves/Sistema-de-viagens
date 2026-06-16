package com.vvv.sistema.controller;

import com.vvv.sistema.model.LoginRequest;
import com.vvv.sistema.model.Usuario;
import com.vvv.sistema.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/login")
    public Usuario login(@RequestBody LoginRequest request) {
        return service.login(request.getEmail(), request.getSenha());
    }

    // cadastrar usuário
    @PostMapping
    public Usuario cadastrar(@RequestBody Usuario usuario) {
        return service.cadastrarUsuario(usuario);
    }

    // listar usuários
    @GetMapping
    public List<Usuario> listar() {
        return service.listarUsuarios();
    }
}
