package com.restaurante01.api_restaurante.cardapio.exceptions;

public class CardapioDataIniMaiorQueDataFimException extends RuntimeException{
    public CardapioDataIniMaiorQueDataFimException(String message){
        super(message);
    }
}
