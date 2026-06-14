package com.vvv.sistema.service;

import com.vvv.sistema.model.Ticket;
import com.vvv.sistema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository repository;

    public Ticket criar(Ticket t) {
        return repository.save(t);
    }

    public List<Ticket> listar() {
        return repository.findAll();
    }
}