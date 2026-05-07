package com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto;

import java.util.List;

public record PedidoCriadoPayload (
    Long idPedido,
    Long idCardapio,
    List<ItemPedidoPayload> itens
)
    {
}
