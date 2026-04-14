package com.restaurante01.api_restaurante.modulos.pedido.dominio.evento;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;

import java.util.List;

public record PedidoCriadoEvento(
        Pedido pedido,
        List<CardapioProduto> estoqueValidado
) {
}
