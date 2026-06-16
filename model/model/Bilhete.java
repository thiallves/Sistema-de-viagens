package com.vvv.sistema.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Bilhete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assento;

    private LocalDateTime embarque;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    public Bilhete() {}

    public Long getId() {
        return id;
    }

    public String getAssento() {
        return assento;
    }

    public void setAssento(String assento) {
        this.assento = assento;
    }

    public LocalDateTime getEmbarque() {
        return embarque;
    }

    public void setEmbarque(LocalDateTime embarque) {
        this.embarque = embarque;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}