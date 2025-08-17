package com.restaurante01.api_restaurante.produto.exceptions;

public class ProdutoDescVazioException extends RuntimeException{
    public ProdutoDescVazioException(String message){
        super(message);
    }
}
