package com.vvv.sistema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vvv.sistema.model.Bilhete;
import com.vvv.sistema.model.Ticket;
import com.vvv.sistema.repository.BilheteRepository;
import com.vvv.sistema.repository.TicketRepository;

@Service
public class BilheteService {

    @Autowired
    private BilheteRepository bilheteRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public Bilhete criar(Bilhete bilhete) {

        if (bilhete.getAssento() == null || bilhete.getAssento().isBlank()) {
            throw new RuntimeException("Assento é obrigatório para gerar bilhete");
        }

        if (bilhete.getTicket() == null || bilhete.getTicket().getId() == null) {
            throw new RuntimeException("Ticket é obrigatório para gerar bilhete");
        }

        Ticket ticket = ticketRepository.findById(bilhete.getTicket().getId())
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));

        if (!"EMITIDO".equalsIgnoreCase(ticket.getStatus())) {
            throw new RuntimeException("Bilhete só pode ser gerado para ticket EMITIDO");
        }

        bilhete.setTicket(ticket);

        if (bilhete.getEmbarque() == null) {
            bilhete.setEmbarque(ticket.getHoraPartida());
        }

        return bilheteRepository.save(bilhete);
    }

    public List<Bilhete> listar() {
        return bilheteRepository.findAll();
    }
}