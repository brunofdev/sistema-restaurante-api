package com.restaurante01.api_restaurante.modulos.pedido.dominio.evento;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ItemPedidoAvaliacaoPayload;

import java.util.List;

public record PedidoEntregueEvento (
    Pedido pedido,
    List<ItemPedidoAvaliacaoPayload> listaDeItensParaAvaliacao
    )
{
}
