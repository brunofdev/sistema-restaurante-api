package com.restaurante01.api_restaurante.produto.exceptions;

public class ProdutoNomeInvalidoException extends  RuntimeException{
    public ProdutoNomeInvalidoException(String message){
        super(message);
    }

}
