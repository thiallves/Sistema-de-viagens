package com.vvv.sistema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vvv.sistema.model.Funcionario;
import com.vvv.sistema.model.PontoVenda;
import com.vvv.sistema.repository.FuncionarioRepository;
import com.vvv.sistema.repository.PontoVendaRepository;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private PontoVendaRepository pontoVendaRepository;

    public Funcionario criar(Funcionario funcionario) {

        if (funcionario.getNome() == null || funcionario.getNome().isBlank()) {
            throw new RuntimeException("Nome do funcionário é obrigatório");
        }

        if (funcionario.getCpf() == null || funcionario.getCpf().isBlank()) {
            throw new RuntimeException("CPF do funcionário é obrigatório");
        }

        if (funcionario.getEmail() == null || funcionario.getEmail().isBlank()) {
            throw new RuntimeException("Email do funcionário é obrigatório");
        }

        if (funcionario.getSenha() == null || funcionario.getSenha().isBlank()) {
            throw new RuntimeException("Senha do funcionário é obrigatória");
        }

        if (funcionario.getIdade() == null || funcionario.getIdade() < 18) {
            throw new RuntimeException("Funcionário deve ter pelo menos 18 anos");
        }

        if (funcionario.getCargo() == null || funcionario.getCargo().isBlank()) {
            throw new RuntimeException("Cargo do funcionário é obrigatório");
        }

        if (!cargoValido(funcionario.getCargo())) {
            throw new RuntimeException("Cargo inválido. Use PADRAO, GERENTE ou GERENTE_NEGOCIOS_VIRTUAIS");
        }

        if (funcionario.getPontoVenda() == null || funcionario.getPontoVenda().getId() == null) {
            throw new RuntimeException("Ponto de venda é obrigatório para funcionário");
        }

        PontoVenda pontoVenda = pontoVendaRepository.findById(funcionario.getPontoVenda().getId())
                .orElseThrow(() -> new RuntimeException("Ponto de venda não encontrado"));

        funcionario.setPontoVenda(pontoVenda);
        funcionario.setTipo("FUNCIONARIO");

        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> listar() {
        return funcionarioRepository.findAll();
    }

    private boolean cargoValido(String cargo) {
        return cargo.equalsIgnoreCase("PADRAO") ||
               cargo.equalsIgnoreCase("GERENTE") ||
               cargo.equalsIgnoreCase("GERENTE_NEGOCIOS_VIRTUAIS");
    }
}