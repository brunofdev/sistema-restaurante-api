package com.restaurante01.api_restaurante.cardapio.exceptions;

public class CardapioNaoEncontradoException extends RuntimeException{
    public CardapioNaoEncontradoException(String message){
        super(message);
    }
}
