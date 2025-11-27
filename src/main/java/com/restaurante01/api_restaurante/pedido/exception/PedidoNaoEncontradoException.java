package com.restaurante01.api_restaurante.pedido.exception;

public class PedidoNaoEncontradoException extends  RuntimeException{
    public PedidoNaoEncontradoException (String message){
        super(message);
    }
}
