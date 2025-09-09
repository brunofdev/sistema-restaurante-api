package com.restaurante01.api_restaurante.cardapio.dto.saida;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public class CardapioDTO {
    @Min(value = 1, message = "Id não pode ser zero (0)")
    @NotNull(message = "O id não pode ser vazio")
    private final Long id;
    @NotBlank(message = "Nome não pode ser vazio")
    private final String nome;
    @NotBlank(message = "Descrição não pode ser vazio")
    private final String descricao;
    private final Boolean disponibilidade;

    @NotNull(message = "A Data de Inicio não pode ser vazia")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate dataInicio;

    @NotNull(message = "A Data de término não pode ser vazia")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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
