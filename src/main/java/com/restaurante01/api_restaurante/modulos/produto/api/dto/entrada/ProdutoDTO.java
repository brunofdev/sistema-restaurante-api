package com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoDTO(
        @NotNull(message = "Preço deve ser informado")
        @Positive
        Long id,

        @NotBlank(message = "Nome não deve ser vazio")
        String nome,

        @NotBlank(message = "Descrição não deve ser vazia")
        String descricao,

        @NotNull(message = "Preço deve ser informado")
        @PositiveOrZero(message = "Preço deve ser zero ou positivo")
        BigDecimal preco,

        @NotNull(message = "Quantidade deve ser zero ou positivo")
        @Min(value = 0, message = "Quantidade minima deve ser zero")
        Integer quantidadeAtual,

        Boolean disponibilidade,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        LocalDateTime dataCriacao,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        LocalDateTime dataAtualizacao,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String criadoPor,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String atualizadoPor
) {}