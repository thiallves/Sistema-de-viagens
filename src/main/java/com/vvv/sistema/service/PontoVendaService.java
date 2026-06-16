package com.vvv.sistema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vvv.sistema.model.PontoVenda;
import com.vvv.sistema.repository.PontoVendaRepository;

@Service
public class PontoVendaService {

    @Autowired
    private PontoVendaRepository repository;

    public PontoVenda criar(PontoVenda pontoVenda) {

        if (pontoVenda.getNome() == null || pontoVenda.getNome().isBlank()) {
            throw new RuntimeException("Nome do ponto de venda é obrigatório");
        }

        if (pontoVenda.getCnpj() == null || pontoVenda.getCnpj().isBlank()) {
            throw new RuntimeException("CNPJ do ponto de venda é obrigatório");
        }

        if (pontoVenda.getEndereco() == null || pontoVenda.getEndereco().isBlank()) {
            throw new RuntimeException("Endereço do ponto de venda é obrigatório");
        }

        if (pontoVenda.getTelefone() == null || pontoVenda.getTelefone().isBlank()) {
            throw new RuntimeException("Telefone do ponto de venda é obrigatório");
        }

        return repository.save(pontoVenda);
    }

    public List<PontoVenda> listar() {
        return repository.findAll();
    }
}