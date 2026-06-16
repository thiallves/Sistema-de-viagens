package com.vvv.sistema.controller;

import com.vvv.sistema.model.Transportadora;
import com.vvv.sistema.service.TransportadoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transportadoras")
public class TransportadoraController {

    @Autowired
    private TransportadoraService service;

    @PostMapping
    public Transportadora criar(@RequestBody Transportadora t) {
        return service.criar(t);
    }

    @GetMapping
    public List<Transportadora> listar() {
        return service.listar();
    }
}
