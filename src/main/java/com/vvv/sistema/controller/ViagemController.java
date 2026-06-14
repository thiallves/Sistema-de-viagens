package com.vvv.sistema.controller;

import com.vvv.sistema.model.Viagem;
import com.vvv.sistema.service.ViagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viagens")
public class ViagemController {

    @Autowired
    private ViagemService service;

    @PostMapping
    public Viagem criar(@RequestBody Viagem viagem) {
        return service.salvar(viagem);
    }

    @GetMapping
    public List<Viagem> listar() {
        return service.listar();
    }
}