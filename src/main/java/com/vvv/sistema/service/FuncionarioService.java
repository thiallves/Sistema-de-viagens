package com.vvv.sistema.service;

import com.vvv.sistema.model.Funcionario;
import com.vvv.sistema.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    public Funcionario criar(Funcionario f) {
        return repository.save(f);
    }

    public List<Funcionario> listar() {
        return repository.findAll();
    }
}