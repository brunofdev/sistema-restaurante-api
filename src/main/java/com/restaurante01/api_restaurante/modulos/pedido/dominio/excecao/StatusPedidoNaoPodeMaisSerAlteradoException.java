package com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class StatusPedidoNaoPodeMaisSerAlteradoException extends RegraDeNegocioExcecao {
    public StatusPedidoNaoPodeMaisSerAlteradoException(String message){
        super(message);
    }
}
