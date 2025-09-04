package com.restaurante01.api_restaurante.cardapioproduto.exceptions;

public class AssociacaoNaoExisteException extends RuntimeException{
    public AssociacaoNaoExisteException (String message){
        super(message);
    }
}
