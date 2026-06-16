package com.vvv.sistema.service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vvv.sistema.model.Reserva;
import com.vvv.sistema.model.Ticket;
import com.vvv.sistema.model.Viagem;
import com.vvv.sistema.repository.ReservaRepository;
import com.vvv.sistema.repository.TicketRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public Ticket criar(Ticket ticket) {

        if (ticket.getReserva() == null || ticket.getReserva().getId() == null) {
            throw new RuntimeException("Reserva é obrigatória para emitir ticket");
        }

        Reserva reserva = reservaRepository.findById(ticket.getReserva().getId())
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        if (!reserva.getStatus().equalsIgnoreCase("CONFIRMADA")) {
            throw new RuntimeException("Ticket só pode ser emitido para reserva CONFIRMADA");
        }

        if (ticketRepository.existsByReservaId(reserva.getId())) {
            throw new RuntimeException("Esta reserva já possui ticket emitido");
        }

        if (reserva.getViagem() == null) {
            throw new RuntimeException("Reserva não possui viagem vinculada");
        }

        Viagem viagem = reserva.getViagem();

        ticket.setReserva(reserva);
        ticket.setStatus("EMITIDO");

        if (ticket.getNumero() == null || ticket.getNumero().isBlank()) {
            ticket.setNumero("TCK-" + System.currentTimeMillis());
        }

        if (ticket.getLocalizador() == null || ticket.getLocalizador().isBlank()) {
            ticket.setLocalizador("VVV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }

        if (ticket.getTipoPassagem() == null || ticket.getTipoPassagem().isBlank()) {
            ticket.setTipoPassagem("RODOVIARIA");
        }

        ticket.setHoraPartida(viagem.getDataPartida());
        ticket.setHoraChegada(viagem.getDataChegada());

        if (viagem.getDataPartida() != null && viagem.getDataChegada() != null) {
            long minutos = Duration.between(viagem.getDataPartida(), viagem.getDataChegada()).toMinutes();
            ticket.setTempoViagem((int) minutos);
        }

        return ticketRepository.save(ticket);
    }

    public List<Ticket> listar() {
        return ticketRepository.findAll();
    }
}