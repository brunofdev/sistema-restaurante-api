package com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class CardapioIdNegativoExcecao extends RegraDeNegocioExcecao {
    public CardapioIdNegativoExcecao(String message){
        super(message);
    }
}
