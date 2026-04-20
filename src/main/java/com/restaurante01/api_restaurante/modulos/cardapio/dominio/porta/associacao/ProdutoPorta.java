package com.restaurante01.api_restaurante.modulos.cardapio.dominio.porta.associacao;

import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;

public interface ProdutoPorta {
    Produto obterProdutoPorId(Long id);

}
