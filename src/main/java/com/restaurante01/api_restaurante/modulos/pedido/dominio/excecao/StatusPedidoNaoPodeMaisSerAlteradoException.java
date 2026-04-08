package com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao;

public class StatusPedidoNaoPodeMaisSerAlteradoException extends  RuntimeException{
    public StatusPedidoNaoPodeMaisSerAlteradoException(String message){
        super(message);
    }
}
