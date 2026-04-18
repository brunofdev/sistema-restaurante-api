package com.restaurante01.api_restaurante.modulos.produto.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class ProdutoNomeInvalidoException extends RegraDeNegocioExcecao {
    public ProdutoNomeInvalidoException(String message){
        super(message);
    }

}
