package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class StatusAvaliacaoNaoPodeSerVazioExcecao extends RegraDeNegocioExcecao {
    public StatusAvaliacaoNaoPodeSerVazioExcecao(String message){
        super(message);
    }
}
