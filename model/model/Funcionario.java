package com.vvv.sistema.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Funcionario extends Usuario {

    private String cargo;

    @ManyToOne
    @JoinColumn(name = "ponto_venda_id")
    private PontoVenda pontoVenda;

    public Funcionario() {}

    public String getCargo() { 
        return cargo; 
    }

    public void setCargo(String cargo) { 
        this.cargo = cargo; 
    }

    public PontoVenda getPontoVenda() { 
        return pontoVenda; 
    }

    public void setPontoVenda(PontoVenda pontoVenda) { 
        this.pontoVenda = pontoVenda; 
    }
}