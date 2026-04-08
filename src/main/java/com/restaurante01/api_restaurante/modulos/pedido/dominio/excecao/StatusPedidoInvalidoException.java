package com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao;

public class StatusPedidoInvalidoException extends  RuntimeException{
    public StatusPedidoInvalidoException(String message){
        super(message);
    }
}
