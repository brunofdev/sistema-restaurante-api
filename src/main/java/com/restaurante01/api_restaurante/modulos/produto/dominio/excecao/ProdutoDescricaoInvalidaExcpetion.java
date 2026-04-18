package com.restaurante01.api_restaurante.modulos.produto.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class ProdutoDescricaoInvalidaExcpetion extends RegraDeNegocioExcecao {
    public ProdutoDescricaoInvalidaExcpetion(String message){
        super(message);
    }
}
