package com.restaurante01.api_restaurante.cardapio.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Table(name = "cardapio")
public class Cardapio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String nome;
    @NotNull
    private String descricao;
    @NotNull
    private boolean disponibilidade;
    @NotNull
    @Column(name = "dtinicio")
    private Date dataInicio;
    @Column(name = "dtfim")
    private Date dataFim;


    public Cardapio(){

    }

    public Cardapio(long id,String nome, String descricao, boolean disponibilidade, Date dataInicio, Date dataFim) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.disponibilidade = disponibilidade;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public boolean getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}