package com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao;

public class CardapioNomeInvalidoException extends RuntimeException{
    public CardapioNomeInvalidoException(String message){
        super(message);
    }
}
