package com.restaurante01.api_restaurante.cardapio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CardapioCreateDTO {
    @NotBlank(message = "O Nome não pode ser vazio")
    private final String nome;
    @NotBlank(message = "A descrição não pode ser vazia")
    private final String descricao;
    @NotNull(message = "A disponibilidade do cardápio deve ser preenchida")
    private final Boolean disponibilidade;
    @NotNull(message = "A Data do cardápio deve ser preenchida")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate dataInicio;
    @NotNull(message = "A Data do cardápio deve ser preenchida")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate dataFim;

    public CardapioCreateDTO(String nome, String descricao, Boolean disponibilidade, LocalDate dataInicio, LocalDate dataFim) {
        this.nome = nome;
        this.descricao = descricao;
        this.disponibilidade = disponibilidade;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boolean getDisponibilidade() {
        return disponibilidade;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }
}
