package com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida;

import java.math.BigDecimal;

public record ItemPedidoDTO(
        String nomeProduto,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subTotal
) {
}
