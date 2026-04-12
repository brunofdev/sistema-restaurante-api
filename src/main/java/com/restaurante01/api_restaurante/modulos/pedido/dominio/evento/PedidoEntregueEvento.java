package com.restaurante01.api_restaurante.modulos.pedido.dominio.evento;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;

public record PedidoEntregueEvento (
    Pedido pedido
    )
{
}
