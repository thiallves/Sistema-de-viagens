package com.vvv.sistema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vvv.sistema.model.Modal;
import com.vvv.sistema.model.Reserva;
import com.vvv.sistema.model.Usuario;
import com.vvv.sistema.model.Viagem;
import com.vvv.sistema.repository.ReservaRepository;
import com.vvv.sistema.repository.UsuarioRepository;
import com.vvv.sistema.repository.ViagemRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ViagemRepository viagemRepository;

    public Reserva criarReserva(Reserva reserva) {

        reserva.setStatus("PENDENTE");

        if (reserva.getValor() == null || reserva.getValor() <= 0) {
            throw new RuntimeException("Valor inválido");
        }

        if (reserva.getUsuario() == null || reserva.getUsuario().getId() == null) {
            throw new RuntimeException("Usuário é obrigatório");
        }

        Usuario usuario = usuarioRepository
                .findById(reserva.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        reserva.setUsuario(usuario);

        if (reserva.getViagem() == null || reserva.getViagem().getId() == null) {
            throw new RuntimeException("Viagem é obrigatória");
        }

        Viagem viagem = viagemRepository
                .findById(reserva.getViagem().getId())
                .orElseThrow(() -> new RuntimeException("Viagem não encontrada"));

        if (viagem.getModal() == null) {
            throw new RuntimeException("A viagem não possui modal associado");
        }

        Modal modal = viagem.getModal();

        if (modal.getStatus() != null && modal.getStatus().equalsIgnoreCase("MANUTENCAO")) {
            throw new RuntimeException("Modal em manutenção não pode ser reservado");
        }

        long totalReservas = repository.countByViagem(viagem);

        if (totalReservas >= modal.getCapacidade()) {
            throw new IllegalStateException("Capacidade da viagem esgotada. Overbooking não permitido.");
        }

        reserva.setViagem(viagem);

        if (usuario.getIdade() != null &&
                usuario.getIdade() >= 2 &&
                usuario.getIdade() <= 10) {

            reserva.setValor(reserva.getValor() * 0.6);
        }

        if (usuario.getIdade() != null && usuario.getIdade() < 10) {

            if (reserva.getAcompanhante() == null ||
                    reserva.getAcompanhante().getId() == null) {
                throw new RuntimeException("Passageiro menor de 10 anos deve ter acompanhante");
            }

            Usuario acompanhante = usuarioRepository
                    .findById(reserva.getAcompanhante().getId())
                    .orElseThrow(() -> new RuntimeException("Acompanhante não encontrado"));

            if (acompanhante.getIdade() == null || acompanhante.getIdade() < 21) {
                throw new RuntimeException("Acompanhante deve ter 21 anos ou mais");
            }

            reserva.setAcompanhante(acompanhante);
        }

        return repository.save(reserva);
    }

    public List<Reserva> listarReservas() {
        return repository.findAll();
    }
}