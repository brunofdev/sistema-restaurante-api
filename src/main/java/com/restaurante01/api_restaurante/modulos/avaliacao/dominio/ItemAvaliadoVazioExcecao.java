package com.restaurante01.api_restaurante.modulos.avaliacao.dominio;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class ItemAvaliadoVazioExcecao extends RegraDeNegocioExcecao {
    public ItemAvaliadoVazioExcecao(String message){
        super(message);
    }
}
