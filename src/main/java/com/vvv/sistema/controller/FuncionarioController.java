package com.vvv.sistema.controller;

import com.vvv.sistema.model.Funcionario;
import com.vvv.sistema.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository repository;

    @PostMapping
    public Funcionario criar(@RequestBody Funcionario f) {
        return repository.save(f);
    }

    @GetMapping
    public List<Funcionario> listar() {
        return repository.findAll();
    }
}