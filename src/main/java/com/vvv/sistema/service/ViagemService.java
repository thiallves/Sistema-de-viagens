package com.vvv.sistema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vvv.sistema.model.Modal;
import com.vvv.sistema.model.Viagem;
import com.vvv.sistema.repository.ModalRepository;
import com.vvv.sistema.repository.ViagemRepository;

@Service
public class ViagemService {

    @Autowired
    private ViagemRepository viagemRepository;

    @Autowired
    private ModalRepository modalRepository;

    public Viagem salvar(Viagem viagem) {

        if (viagem.getOrigem() == null || viagem.getOrigem().isBlank()) {
            throw new RuntimeException("Origem é obrigatória");
        }

        if (viagem.getDestino() == null || viagem.getDestino().isBlank()) {
            throw new RuntimeException("Destino é obrigatório");
        }

        if (viagem.getDataPartida() == null) {
            throw new RuntimeException("Data de partida é obrigatória");
        }

        if (viagem.getDataChegada() == null) {
            throw new RuntimeException("Data de chegada é obrigatória");
        }

        if (viagem.getDataChegada().isBefore(viagem.getDataPartida())) {
            throw new RuntimeException("Data de chegada não pode ser antes da data de partida");
        }

        if (viagem.getModal() == null || viagem.getModal().getId() == null) {
            throw new RuntimeException("Modal é obrigatório para cadastrar viagem");
        }

        Modal modal = modalRepository
                .findById(viagem.getModal().getId())
                .orElseThrow(() -> new RuntimeException("Modal não encontrado"));

        viagem.setModal(modal);

        return viagemRepository.save(viagem);
    }

    public List<Viagem> listar() {
        return viagemRepository.findAll();
    }
}