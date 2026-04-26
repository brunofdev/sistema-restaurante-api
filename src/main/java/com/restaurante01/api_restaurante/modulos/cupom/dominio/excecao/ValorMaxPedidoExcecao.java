package com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class ValorMaxPedidoExcecao extends RegraDeNegocioExcecao {
    public ValorMaxPedidoExcecao(String message){
        super(message);
    }
}
