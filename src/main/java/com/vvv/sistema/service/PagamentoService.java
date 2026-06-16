package com.vvv.sistema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vvv.sistema.model.Pagamento;
import com.vvv.sistema.model.Reserva;
import com.vvv.sistema.repository.PagamentoRepository;
import com.vvv.sistema.repository.ReservaRepository;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public Pagamento realizarPagamento(Pagamento pagamento) {

        if (pagamento.getReserva() == null || pagamento.getReserva().getId() == null) {
            throw new RuntimeException("Reserva é obrigatória para realizar pagamento");
        }

        if (pagamento.getMetodo() == null || pagamento.getMetodo().isBlank()) {
            throw new RuntimeException("Método de pagamento é obrigatório");
        }

        if (!pagamento.getMetodo().equalsIgnoreCase("CREDITO") &&
            !pagamento.getMetodo().equalsIgnoreCase("DEBITO")) {
            throw new RuntimeException("Método de pagamento inválido. Use CREDITO ou DEBITO");
        }

        if (pagamento.getParcelas() == null || pagamento.getParcelas() <= 0) {
            throw new RuntimeException("Quantidade de parcelas inválida");
        }

        if (pagamento.getMetodo().equalsIgnoreCase("DEBITO") && pagamento.getParcelas() > 1) {
            throw new RuntimeException("Pagamento em débito não pode ser parcelado");
        }

        Reserva reserva = reservaRepository.findById(pagamento.getReserva().getId())
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        if (!reserva.getStatus().equalsIgnoreCase("PENDENTE")) {
            throw new RuntimeException("A reserva precisa estar PENDENTE para ser paga");
        }

        if (pagamentoRepository.existsByReservaId(reserva.getId())) {
            throw new RuntimeException("Esta reserva já possui pagamento registrado");
        }

        Double valorFinal = reserva.getValor();

        if (pagamento.getMetodo().equalsIgnoreCase("CREDITO") && pagamento.getParcelas() > 4) {
            valorFinal = valorFinal * 1.05;
        }

        pagamento.setValor(valorFinal);
        pagamento.setStatus("APROVADO");

        reserva.setStatus("CONFIRMADA");
        reservaRepository.save(reserva);

        pagamento.setReserva(reserva);

        return pagamentoRepository.save(pagamento);
    }

    public List<Pagamento> listarPagamentos() {
        return pagamentoRepository.findAll();
    }
}