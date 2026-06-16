package com.vvv.sistema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vvv.sistema.model.PontoVenda;
import com.vvv.sistema.repository.PontoVendaRepository;
import com.vvv.sistema.service.PontoVendaService;

@RestController
@RequestMapping("/pontovendas")
public class PontoVendaController {

    @Autowired
    private PontoVendaService service;

    @Autowired
    private PontoVendaRepository repository;

    @PostMapping
    public PontoVenda criar(@RequestBody PontoVenda pontoVenda) {
        return service.criar(pontoVenda);
    }

    @GetMapping
    public List<PontoVenda> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public PontoVenda buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ponto de venda não encontrado"));
    }

    @PutMapping("/{id}")
    public PontoVenda atualizar(@PathVariable Long id, @RequestBody PontoVenda dados) {
        PontoVenda pontoVenda = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ponto de venda não encontrado"));

        pontoVenda.setNome(dados.getNome());
        pontoVenda.setCnpj(dados.getCnpj());
        pontoVenda.setEndereco(dados.getEndereco());
        pontoVenda.setTelefone(dados.getTelefone());

        return repository.save(pontoVenda);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Ponto de venda não encontrado");
        }

        repository.deleteById(id);
    }
}