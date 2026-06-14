package com.vvv.sistema.service;

import com.vvv.sistema.model.Viagem;
import com.vvv.sistema.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViagemService {

    @Autowired
    private ViagemRepository repository;

    public Viagem salvar(Viagem viagem) {
        return repository.save(viagem);
    }

    public List<Viagem> listar() {
        return repository.findAll();
    }
}