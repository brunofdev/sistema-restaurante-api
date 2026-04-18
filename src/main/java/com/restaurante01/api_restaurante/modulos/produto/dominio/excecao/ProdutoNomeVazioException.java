package com.restaurante01.api_restaurante.modulos.produto.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class ProdutoNomeVazioException extends RegraDeNegocioExcecao {
    public ProdutoNomeVazioException(String message){
        super(message);
    }
}
