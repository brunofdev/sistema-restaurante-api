package com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class CardapioMesmoNomeExcepetion extends RegraDeNegocioExcecao {
    public CardapioMesmoNomeExcepetion (String message){
        super(message);
    }
}
