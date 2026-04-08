package com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao;

public class CardapioNaoEncontradoException extends RuntimeException{
    public CardapioNaoEncontradoException(String message){
        super(message);
    }
}
