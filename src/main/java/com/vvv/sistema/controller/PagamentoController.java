package com.vvv.sistema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vvv.sistema.model.Pagamento;
import com.vvv.sistema.service.PagamentoService;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @PostMapping
    public Pagamento pagar(@RequestBody Pagamento pagamento) {
        return service.realizarPagamento(pagamento);
    }

    @GetMapping
    public List<Pagamento> listar() {
        return service.listarPagamentos();
    }
}