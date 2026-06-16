package com.vvv.sistema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vvv.sistema.model.Funcionario;
import com.vvv.sistema.service.FuncionarioService;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @PostMapping
    public Funcionario criar(@RequestBody Funcionario funcionario) {
        return service.criar(funcionario);
    }

    @GetMapping
    public List<Funcionario> listar() {
        return service.listar();
    }
}