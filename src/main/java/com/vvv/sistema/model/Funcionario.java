/*package com.vvv.sistema.model;

import jakarta.persistence.*;

@Entity
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String endereco;

    private String cargo;

    @ManyToOne
    @JoinColumn(name = "ponto_venda_id")
    private PontoVenda pontoVenda;

    public Funcionario() {}

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

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
}*/

package com.vvv.sistema.model;

import jakarta.persistence.*;

@Entity
public class Funcionario extends Usuario {

    private String cargo;

    @ManyToOne
    private PontoVenda pontoVenda;

    public Funcionario() {}

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public PontoVenda getPontoVenda() { return pontoVenda; }
    public void setPontoVenda(PontoVenda pontoVenda) { this.pontoVenda = pontoVenda; }
}