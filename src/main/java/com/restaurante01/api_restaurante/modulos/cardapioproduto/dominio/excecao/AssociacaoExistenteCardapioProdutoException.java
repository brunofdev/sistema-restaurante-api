package com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class AssociacaoExistenteCardapioProdutoException extends RegraDeNegocioExcecao {
    public AssociacaoExistenteCardapioProdutoException(String message){
        super(message);
    }
}
