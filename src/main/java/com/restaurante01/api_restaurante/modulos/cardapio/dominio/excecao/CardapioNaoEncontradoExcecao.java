package com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class CardapioNaoEncontradoExcecao extends RegraDeNegocioExcecao {
    public CardapioNaoEncontradoExcecao(String message){
        super(message);
    }
}
