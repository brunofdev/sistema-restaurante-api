package com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto;

public record ItemPedidoClientePayload(
        Long idProduto,
        Integer quantidade
) {}

