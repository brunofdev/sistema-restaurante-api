package com.restaurante01.api_restaurante.pedido.exception;

public class StatusPedidoInvalidoException extends  RuntimeException{
    public StatusPedidoInvalidoException(String message){
        super(message);
    }
}
