package com.vvv.sistema.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String status;
    private LocalDateTime dataVenda;

    private String metodoPagamento;
    private Integer parcelas;
    private Double valorFinal;

    // ligação com reserva
    @OneToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

    //funcionário (venda presencial)
    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    public Venda() {
        this.dataVenda = LocalDateTime.now();
        this.status = "PENDENTE";
    }

    public Long getId() { return id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getDataVenda() { return dataVenda; }

    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public Integer getParcelas() { return parcelas; }
    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public Double getValorFinal() { return valorFinal; }
    public void setValorFinal(Double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public Reserva getReserva() { return reserva; }
    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}