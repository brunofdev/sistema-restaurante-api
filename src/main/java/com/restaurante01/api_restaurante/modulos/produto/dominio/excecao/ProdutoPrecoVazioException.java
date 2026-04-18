package com.restaurante01.api_restaurante.modulos.produto.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class ProdutoPrecoVazioException extends RegraDeNegocioExcecao {
    public ProdutoPrecoVazioException(String message){
        super(message);
    }
}
