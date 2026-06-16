package com.vvv.sistema.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    private String tipoPassagem;

    private String localizador;

    private LocalDateTime horaPartida;
    private LocalDateTime horaChegada;

    private Integer tempoViagem;

    private String status; // EMITIDO ou CANCELADO

    @OneToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

    public Ticket() {
        this.numero = "TCK-" + System.currentTimeMillis();
        this.status = "EMITIDO";
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipoPassagem() {
        return tipoPassagem;
    }

    public void setTipoPassagem(String tipoPassagem) {
        this.tipoPassagem = tipoPassagem;
    }

    public String getLocalizador() {
        return localizador;
    }

    public void setLocalizador(String localizador) {
        this.localizador = localizador;
    }

    public LocalDateTime getHoraPartida() {
        return horaPartida;
    }

    public void setHoraPartida(LocalDateTime horaPartida) {
        this.horaPartida = horaPartida;
    }

    public LocalDateTime getHoraChegada() {
        return horaChegada;
    }

    public void setHoraChegada(LocalDateTime horaChegada) {
        this.horaChegada = horaChegada;
    }

    public Integer getTempoViagem() {
        return tempoViagem;
    }

    public void setTempoViagem(Integer tempoViagem) {
        this.tempoViagem = tempoViagem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
}