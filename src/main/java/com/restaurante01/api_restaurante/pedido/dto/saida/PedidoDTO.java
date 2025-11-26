package com.restaurante01.api_restaurante.pedido.dto.saida;

import com.restaurante01.api_restaurante.pedido.Enum.StatusPedido;

import java.math.BigDecimal;
import java.util.List;

public record PedidoDTO(
        Long id,
        String nomeUsuario,
        String cpfUsuario,
        String whatsappUsuario,
        List<ItemPedidoDTO> itens,
        BigDecimal valorTotal,
        StatusPedido statusPedido
) {
}
