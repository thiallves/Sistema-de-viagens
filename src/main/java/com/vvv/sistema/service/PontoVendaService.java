package com.vvv.sistema.service;

import com.vvv.sistema.model.PontoVenda;
import com.vvv.sistema.repository.PontoVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PontoVendaService {

    @Autowired
    private PontoVendaRepository repository;

    public PontoVenda criar(PontoVenda p) {
        return repository.save(p);
    }

    public List<PontoVenda> listar() {
        return repository.findAll();
    }
}
