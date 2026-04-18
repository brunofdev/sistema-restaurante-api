package com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class CardapioIdNegativoException extends RegraDeNegocioExcecao {
    public CardapioIdNegativoException(String message){
        super(message);
    }
}
