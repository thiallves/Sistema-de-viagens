package com.vvv.sistema.service;

import com.vvv.sistema.model.Modal;
import com.vvv.sistema.model.Reserva;
import com.vvv.sistema.model.Usuario;
import com.vvv.sistema.repository.ModalRepository;
import com.vvv.sistema.repository.ReservaRepository;
import com.vvv.sistema.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModalRepository modalRepository;

    public Reserva criarReserva(Reserva reserva) {

        reserva.setStatus("PENDENTE");

        if (reserva.getValor() == null || reserva.getValor() <= 0) {
            throw new RuntimeException("Valor inválido");
        }

        // buscar usuário
        if (reserva.getUsuario() != null && reserva.getUsuario().getId() != null) {

            Usuario usuario = usuarioRepository
                    .findById(reserva.getUsuario().getId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            reserva.setUsuario(usuario);

            // desconto por idade + validação de acompanhante
            if (usuario.getIdade() != null &&
                usuario.getIdade() > 2 &&
                usuario.getIdade() < 10) {

                // validar acompanhante obrigatório
                if (reserva.getAcompanhante() == null ||
                    reserva.getAcompanhante().getId() == null) {
                    throw new RuntimeException("Criança deve viajar com um acompanhante");
                }

                Usuario acompanhante = usuarioRepository
                        .findById(reserva.getAcompanhante().getId())
                        .orElseThrow(() -> new RuntimeException("Acompanhante não encontrado"));

                // validar idade do acompanhante
                if (acompanhante.getIdade() == null || acompanhante.getIdade() < 21) {
                    throw new RuntimeException("Acompanhante deve ter 21 anos ou mais");
                }

                reserva.setAcompanhante(acompanhante);

                // aplica desconto de 40%
                reserva.setValor(reserva.getValor() * 0.6);
            }
        }

        // buscar modal
        if (reserva.getModal() != null && reserva.getModal().getId() != null) {

            Modal modal = modalRepository
                    .findById(reserva.getModal().getId())
                    .orElseThrow(() -> new RuntimeException("Modal não encontrado"));

            long totalReservas = repository.countByModal(modal);

            if (totalReservas >= modal.getCapacidade()) {
                throw new IllegalStateException("Capacidade do modal esgotada (overbooking não permitido)");
            }

            reserva.setModal(modal);
        }

        return repository.save(reserva);
    }

    public List<Reserva> listarReservas() {
        return repository.findAll();
    }
}
