package com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao;

public class CardapioIdNegativoException extends  RuntimeException{
    public CardapioIdNegativoException(String message){
        super(message);
    }
}
