package com.vvv.sistema.controller;

import com.vvv.sistema.model.Ticket;
import com.vvv.sistema.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService service;

    @PostMapping
    public Ticket criar(@RequestBody Ticket t) {
        return service.criar(t);
    }

    @GetMapping
    public List<Ticket> listar() {
        return service.listar();
    }
}