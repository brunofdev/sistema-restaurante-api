package com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class CardapioDataIniMaiorQueDataFimException extends RegraDeNegocioExcecao {
    public CardapioDataIniMaiorQueDataFimException(String message){
        super(message);
    }
}
