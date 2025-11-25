package com.restaurante01.api_restaurante.pedido.dto.saida;

import com.restaurante01.api_restaurante.pedido.Enum.StatusPedido;

import java.math.BigDecimal;

public record PedidoDTO(
        Long id,
        String nomeUsuario,
        BigDecimal valorTotal,
        StatusPedido statusPedido
) {
}
