package com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida;

import org.springframework.data.relational.core.sql.In;

import java.math.BigDecimal;

public record ItemPedidoMaisVendidoSemanal(
        Long idProduto,
        String nomeProduto,
        Integer qtdVendida,
        BigDecimal precoMedio,
        Integer qntVezesPedido
) {

}
