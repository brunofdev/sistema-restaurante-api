package com.restaurante01.api_restaurante.cardapio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;


public class CardapioDTO {
    private final Long id;
    private final String nome;
    private final String descricao;
    private final Boolean disponibilidade;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private final LocalDate dataInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private final LocalDate dataFim;

    public CardapioDTO(Long id, String nome, String descricao, Boolean disponibilidade, LocalDate dataInicio, LocalDate dataFim) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.disponibilidade = disponibilidade;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }
    public Long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public LocalDate getDataInicio() {
        return dataInicio;
    }
    public Boolean getDisponibilidade() {
        return disponibilidade;
    }
    public LocalDate getDataFim() {
        return dataFim;
    }


}
