package com.vvv.sistema.controller;

import com.vvv.sistema.model.PontoVenda;
import com.vvv.sistema.repository.PontoVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pontovendas")
public class PontoVendaController {

    @Autowired
    private PontoVendaRepository repository;

    @PostMapping
    public PontoVenda criar(@RequestBody PontoVenda p) {
        return repository.save(p);
    }

    @GetMapping
    public List<PontoVenda> listar() {
        return repository.findAll();
    }
}
