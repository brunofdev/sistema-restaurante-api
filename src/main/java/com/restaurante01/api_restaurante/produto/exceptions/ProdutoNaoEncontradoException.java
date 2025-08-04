package com.restaurante01.api_restaurante.produto.exceptions;

public class ProdutoNaoEncontradoException extends RuntimeException{
    public ProdutoNaoEncontradoException (String message){
        super(message);
    }
}
