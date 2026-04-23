package com.restaurante01.api_restaurante.modulos.produto.api.dto.entrada;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CriarProdutoDTO(
        @NotBlank(message = "Nome não deve ser vazio")
        String nome,

        @NotBlank(message = "Descrição não deve ser vazia")
        String descricao,

        @NotNull(message = "Preço deve ser informado")
        @PositiveOrZero(message = "Preço deve ser zero ou positivo")
        BigDecimal preco,

        @NotNull(message = "Quantidade deve ser zero ou positivo")
        @Min(value = 0, message = "Quantidade minima deve ser zero")
        Long quantidadeAtual,

        Boolean disponibilidade
) {}