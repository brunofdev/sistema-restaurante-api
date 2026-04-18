package com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.porta;

import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;

public interface ProdutoPorta {
    Produto obterProdutoPorId(Long id);

}
