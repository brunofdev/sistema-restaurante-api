package com.restaurante01.api_restaurante.cardapio.exceptions;

public class CardapioSemNomeException extends RuntimeException{
    public CardapioSemNomeException (String message){
        super(message);
    }
}
