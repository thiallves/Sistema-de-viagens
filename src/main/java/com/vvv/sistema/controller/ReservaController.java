package com.vvv.sistema.controller;

import com.vvv.sistema.model.Reserva;
import com.vvv.sistema.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService service;

    // criar reserva
    @PostMapping
    public Reserva criar(@RequestBody Reserva reserva) {
        return service.criarReserva(reserva);
    }

    // listar reservas
    @GetMapping
    public List<Reserva> listar() {
        return service.listarReservas();
    }
}