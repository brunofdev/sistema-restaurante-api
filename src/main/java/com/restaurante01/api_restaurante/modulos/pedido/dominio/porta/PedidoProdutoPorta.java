package com.restaurante01.api_restaurante.modulos.pedido.dominio.porta;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.RepresentacaoProdutoItemPedido;

public interface PedidoProdutoPorta {
    RepresentacaoProdutoItemPedido obterProdutoVendido(Long id);
}
