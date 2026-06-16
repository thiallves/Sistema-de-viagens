package com.vvv.sistema.controller;

import com.vvv.sistema.model.Venda;
import com.vvv.sistema.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService service;

    @PostMapping
    public Venda registrar(@RequestBody Venda venda) {
        return service.registrarVenda(venda);
    }

    @GetMapping
    public List<Venda> listar() {
        return service.listarVendas();
    }

    
    @PutMapping("/{id}/aprovar")
    public Venda aprovar(@PathVariable Long id, @RequestParam Long usuarioId) {
        return service.aprovarVenda(id, usuarioId);
    }

    @PutMapping("/{id}/rejeitar")
    public Venda rejeitar(@PathVariable Long id, @RequestParam Long usuarioId) {
        return service.rejeitarVenda(id, usuarioId);
    }
}
