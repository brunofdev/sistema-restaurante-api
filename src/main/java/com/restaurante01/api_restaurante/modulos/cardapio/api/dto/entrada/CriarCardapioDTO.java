package com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CriarCardapioDTO(
        @NotBlank(message = "O Nome não pode ser vazio") String nome,
        @NotBlank(message = "A descrição não pode ser vazia") String descricao,
        @NotNull(message = "A disponibilidade do cardápio deve ser preenchida") Boolean disponibilidade,
        @NotNull(message = "A Data do cardápio deve ser preenchida")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate dataInicio,
        @NotNull(message = "A Data do cardápio deve ser preenchida")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate dataFim
) {}
