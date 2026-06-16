package com.vvv.sistema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vvv.sistema.model.Ticket;
import com.vvv.sistema.service.TicketService;

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