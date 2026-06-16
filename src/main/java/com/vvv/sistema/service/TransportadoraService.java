package com.vvv.sistema.service;

import com.vvv.sistema.model.Transportadora;
import com.vvv.sistema.repository.TransportadoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportadoraService {

    @Autowired
    private TransportadoraRepository repository;

    public Transportadora criar(Transportadora t) {
        return repository.save(t);
    }

    public List<Transportadora> listar() {
        return repository.findAll();
    }
}