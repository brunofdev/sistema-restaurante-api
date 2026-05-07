package com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AssociacaoEntradaDTO(
        @NotNull(message = "ID do cardapio deve ser enviada") @Min(1) Long idCardapio,
        @Min(1) @NotNull(message = "ID do produto deve ser enviada") Long idProduto,
        @Min(0) BigDecimal precoCustomizado,
        @Min(0) Integer quantidadeCustomizada,
        String descricaoCustomizada,
        Boolean disponibilidadeCustomizada,
        String observacao
) {}
