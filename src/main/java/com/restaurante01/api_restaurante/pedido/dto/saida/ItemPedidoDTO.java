package com.restaurante01.api_restaurante.pedido.dto.saida;

import java.math.BigDecimal;

public record ItemPedidoDTO(
        String nomeProduto,
        String quantidade,
        BigDecimal subTotal
) {
}
