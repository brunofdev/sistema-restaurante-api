package com.restaurante01.api_restaurante.produto.exceptions;

public class ProdutoPrecoVazioException extends RuntimeException{
    public ProdutoPrecoVazioException(String message){
        super(message);
    }
}
