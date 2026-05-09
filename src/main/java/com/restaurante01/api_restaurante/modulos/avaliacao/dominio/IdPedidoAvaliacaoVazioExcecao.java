package com.restaurante01.api_restaurante.modulos.avaliacao.dominio;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class IdPedidoAvaliacaoVazioExcecao extends RegraDeNegocioExcecao {
    public IdPedidoAvaliacaoVazioExcecao(String message){
        super(message);
    }
}
