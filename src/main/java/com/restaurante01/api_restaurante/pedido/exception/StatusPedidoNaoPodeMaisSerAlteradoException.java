package com.restaurante01.api_restaurante.pedido.exception;

public class StatusPedidoNaoPodeMaisSerAlteradoException extends  RuntimeException{
    public StatusPedidoNaoPodeMaisSerAlteradoException(String message){
        super(message);
    }
}
