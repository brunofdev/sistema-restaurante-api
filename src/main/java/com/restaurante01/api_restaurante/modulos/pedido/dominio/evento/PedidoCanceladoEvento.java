package com.restaurante01.api_restaurante.modulos.pedido.dominio.evento;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ItemPedidoPayload;

import java.util.List;

public record PedidoCanceladoEvento (
        Pedido pedido,
        List<ItemPedidoPayload> itensPedidoPayload
){
}
