package com.restaurante01.api_restaurante.cardapio.exceptions;

public class CardapioSemDescException extends RuntimeException{
    public CardapioSemDescException(String message){
        super(message);
    }
}
