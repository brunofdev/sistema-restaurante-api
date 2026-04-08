package com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao;

public class PedidoNaoEncontradoException extends  RuntimeException{
    public PedidoNaoEncontradoException (String message){
        super(message);
    }
}
