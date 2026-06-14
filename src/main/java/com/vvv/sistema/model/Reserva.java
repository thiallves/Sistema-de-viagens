package com.vvv.sistema.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.vvv.sistema.model.Usuario;

@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private Double valor;

    // data em que a reserva foi criada
    private LocalDateTime dataReserva;

    // dados da viagem
    private LocalDateTime dataPartida;
    private LocalDateTime dataChegada;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "modal_id")
    private Modal modal;

    @ManyToOne
    @JoinColumn(name = "ponto_venda_id")
    private PontoVenda pontoVenda;

    @ManyToOne
    @JoinColumn(name = "acompanhante_id")
    private Usuario acompanhante;

    public Usuario getAcompanhante() {
        return acompanhante;
    }

    public void setAcompanhante(Usuario acompanhante) {
        this.acompanhante = acompanhante;
    }

    public Reserva() {
        this.dataReserva = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDateTime dataReserva) {
        this.dataReserva = dataReserva;
    }

    public LocalDateTime getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(LocalDateTime dataPartida) {
        this.dataPartida = dataPartida;
    }

    public LocalDateTime getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(LocalDateTime dataChegada) {
        this.dataChegada = dataChegada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Modal getModal() {
        return modal;
    }

    public void setModal(Modal modal) {
        this.modal = modal;
    }

    public PontoVenda getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(PontoVenda pontoVenda) {
        this.pontoVenda = pontoVenda;
    }
}