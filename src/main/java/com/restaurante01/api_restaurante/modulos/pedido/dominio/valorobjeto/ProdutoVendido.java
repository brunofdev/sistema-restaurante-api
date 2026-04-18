package com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto;

import java.math.BigDecimal;

public record ProdutoVendido(
        RepresentacaoProdutoItemPedido produto,
        BigDecimal precoDeVenda
) {
}
