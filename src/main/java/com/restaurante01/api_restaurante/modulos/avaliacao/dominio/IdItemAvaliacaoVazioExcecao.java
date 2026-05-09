package com.restaurante01.api_restaurante.modulos.avaliacao.dominio;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class IdItemAvaliacaoVazioExcecao extends RegraDeNegocioExcecao {
    public IdItemAvaliacaoVazioExcecao(String message){
        super(message);
    }
}
