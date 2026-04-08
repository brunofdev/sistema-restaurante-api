package com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao;

public class CardapioDataIniMaiorQueDataFimException extends RuntimeException{
    public CardapioDataIniMaiorQueDataFimException(String message){
        super(message);
    }
}
