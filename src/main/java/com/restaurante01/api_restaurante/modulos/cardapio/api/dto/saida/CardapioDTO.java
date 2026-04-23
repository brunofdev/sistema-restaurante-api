package com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public record CardapioDTO(
        @Min(value = 1, message = "Id não pode ser zero (0)") @NotNull(message = "O id não pode ser vazio") Long id,
        @NotBlank(message = "Nome não pode ser vazio") String nome,
        @NotBlank(message = "Descrição não pode ser vazio") String descricao, Boolean disponibilidade,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") @NotNull(message = "A Data de Inicio não pode ser vazia") LocalDate dataInicio,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") @NotNull(message = "A Data de término não pode ser vazia") LocalDate dataFim) {
    public CardapioDTO(Long id, String nome, String descricao, Boolean disponibilidade, LocalDate dataInicio, LocalDate dataFim) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.disponibilidade = disponibilidade;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }
}
