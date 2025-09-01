package com.restaurante01.api_restaurante.cardapio.exceptions;

public class CardapioNomeInvalidoException extends RuntimeException{
    public CardapioNomeInvalidoException(String message){
        super(message);
    }
}
