package com.restaurante01.api_restaurante.produto.exceptions;

public class ProdutoQntdNegativa extends RuntimeException{
    public ProdutoQntdNegativa(String message){
        super(message);
    }
}
