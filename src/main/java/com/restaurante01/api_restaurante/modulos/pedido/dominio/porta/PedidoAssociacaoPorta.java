package com.restaurante01.api_restaurante.modulos.pedido.dominio.porta;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ItemValidacaoEstoque;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ProdutoVendido;

import java.util.List;

public interface PedidoAssociacaoPorta {
    ProdutoVendido obterProdutoVendido(Long idCardapio, Long idProduto);
    void validarEstoque(Long idCardapio, List<ItemValidacaoEstoque> itens);
}
