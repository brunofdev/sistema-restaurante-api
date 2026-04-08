package com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.excecao;

public class AssociacaoNaoExisteException extends RuntimeException{
    public AssociacaoNaoExisteException (String message){
        super(message);
    }
}
