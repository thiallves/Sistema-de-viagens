package com.vvv.sistema.controller;

import com.vvv.sistema.model.Modal;
import com.vvv.sistema.service.ModalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modais")
public class ModalController {

    @Autowired
    private ModalService service;

    @PostMapping
    public Modal criar(@RequestBody Modal modal) {
        return service.salvar(modal);
    }

    @GetMapping
    public List<Modal> listar() {
        return service.listar();
    }
}
