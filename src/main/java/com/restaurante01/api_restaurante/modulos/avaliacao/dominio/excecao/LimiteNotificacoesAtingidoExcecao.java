package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class LimiteNotificacoesAtingidoExcecao extends RegraDeNegocioExcecao {
    public LimiteNotificacoesAtingidoExcecao(String message){
        super(message);
    }
}
