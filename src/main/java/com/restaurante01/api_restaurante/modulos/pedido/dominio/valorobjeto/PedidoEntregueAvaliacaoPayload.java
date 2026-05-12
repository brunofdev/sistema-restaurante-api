package com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto;

import java.util.List;

public record PedidoEntregueAvaliacaoPayload(
        Long pedidoId,
        Long clienteId,
        List<ItemPedidoAvaliacaoPayload> listaDeItensPedido
) {
}
