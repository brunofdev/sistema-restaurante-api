package com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao;

public class ValorItemPedidoIncorretoExcecao extends  RuntimeException{
    public ValorItemPedidoIncorretoExcecao(String message){
        super(message);
    }
}
