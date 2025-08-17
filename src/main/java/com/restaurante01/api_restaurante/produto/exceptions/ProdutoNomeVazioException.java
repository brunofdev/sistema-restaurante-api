package com.restaurante01.api_restaurante.produto.exceptions;

public class ProdutoNomeVazioException extends RuntimeException{
    public ProdutoNomeVazioException(String message){
        super(message);
    }
}
