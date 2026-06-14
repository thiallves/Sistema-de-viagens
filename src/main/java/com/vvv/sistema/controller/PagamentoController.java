package com.vvv.sistema.controller;

import com.vvv.sistema.model.Pagamento;
import com.vvv.sistema.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @PostMapping
    public Pagamento pagar(@RequestBody Pagamento pagamento) {
        return service.realizarPagamento(pagamento);
    }
}