package com.vvv.sistema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vvv.sistema.model.Bilhete;
import com.vvv.sistema.service.BilheteService;

@RestController
@RequestMapping("/bilhetes")
public class BilheteController {

    @Autowired
    private BilheteService service;

    @PostMapping
    public Bilhete criar(@RequestBody Bilhete bilhete) {
        return service.criar(bilhete);
    }

    @GetMapping
    public List<Bilhete> listar() {
        return service.listar();
    }
}