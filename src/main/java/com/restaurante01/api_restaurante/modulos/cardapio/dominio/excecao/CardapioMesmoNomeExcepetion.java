package com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao;

public class CardapioMesmoNomeExcepetion extends RuntimeException{
    public CardapioMesmoNomeExcepetion (String message){
        super(message);
    }
}
