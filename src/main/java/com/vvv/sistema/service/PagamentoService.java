package com.vvv.sistema.service;

import com.vvv.sistema.model.Pagamento;
import com.vvv.sistema.model.Reserva;
import com.vvv.sistema.repository.PagamentoRepository;
import com.vvv.sistema.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public Pagamento realizarPagamento(Pagamento pagamento) {

        // BUSCAR RESERVA COMPLETA NO BANCO
        Reserva reserva = reservaRepository.findById(pagamento.getReserva().getId())
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        Double valorFinal = reserva.getValor();

        // REGRA DE JUROS
        if (pagamento.getParcelas() != null && pagamento.getParcelas() > 4) {
            valorFinal = valorFinal * 1.05;
        }

        pagamento.setValor(valorFinal);
        pagamento.setStatus("APROVADO");

        // atualizar reserva
        reserva.setStatus("CONFIRMADA");
        reservaRepository.save(reserva);

        // vincular reserva ao pagamento
        pagamento.setReserva(reserva);

        return pagamentoRepository.save(pagamento);
    }
}
