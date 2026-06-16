package com.vvv.sistema.service;

import com.vvv.sistema.model.Modal;
import com.vvv.sistema.repository.ModalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModalService {

    @Autowired
    private ModalRepository repository;

    public Modal salvar(Modal modal) {
        return repository.save(modal);
    }

    public List<Modal> listar() {
        return repository.findAll();
    }

    public List<Modal> listarAtivos() {
        return repository.findAll()
                .stream()
                .filter(m -> "ATIVO".equalsIgnoreCase(m.getStatus()))
                .toList();
    }
}
