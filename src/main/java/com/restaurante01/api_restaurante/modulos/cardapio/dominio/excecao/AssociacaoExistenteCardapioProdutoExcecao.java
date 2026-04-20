package com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class AssociacaoExistenteCardapioProdutoExcecao extends RegraDeNegocioExcecao {
    public AssociacaoExistenteCardapioProdutoExcecao(String message){
        super(message);
    }
}
