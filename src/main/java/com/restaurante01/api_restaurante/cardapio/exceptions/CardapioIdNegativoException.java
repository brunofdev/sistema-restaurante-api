package com.restaurante01.api_restaurante.cardapio.exceptions;

public class CardapioIdNegativoException extends  RuntimeException{
    public CardapioIdNegativoException(String message){
        super(message);
    }
}
