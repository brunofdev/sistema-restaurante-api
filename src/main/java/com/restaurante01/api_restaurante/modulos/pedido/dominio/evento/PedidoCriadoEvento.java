package com.restaurante01.api_restaurante.modulos.pedido.dominio.evento;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ItemPedidoClientePayload;

import java.util.List;


public record PedidoCriadoEvento(
        Pedido pedido,
        List<ItemPedidoClientePayload> ItensPedidoPayload
) {
}
