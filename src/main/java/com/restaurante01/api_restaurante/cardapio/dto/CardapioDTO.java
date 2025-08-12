package com.restaurante01.api_restaurante.cardapio.dto;

import java.util.Date;

public class CardapioDTO {
    private final long id;
    private final String nome;
    private final String descricao;
    private final boolean disponibilidade;
    private final Date dataInicio;
    private final Date dataFim;

    public CardapioDTO(long id, String nome, String descricao, boolean disponibilidade, Date dataInicio, Date dataFim) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.disponibilidade = disponibilidade;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }
    public long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public Date getDataInicio() {
        return dataInicio;
    }
    public boolean isDisponibilidade() {
        return disponibilidade;
    }
    public Date getDataFim() {
        return dataFim;
    }


}
