package com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class PedidoNaoEncontradoExcecao extends RegraDeNegocioExcecao {
    public PedidoNaoEncontradoExcecao(String message){
        super(message);
    }
}
