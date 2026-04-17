package com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao;

public class EnderecoDoPedidoInvalidoExcecao extends  RuntimeException{
    public EnderecoDoPedidoInvalidoExcecao(String message){
        super(message);
    }
}
