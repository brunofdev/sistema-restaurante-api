package com.restaurante01.api_restaurante.cardapioproduto.exceptions;

public class NaoExisteAssociacaoException extends RuntimeException{
    public NaoExisteAssociacaoException(String message){
        super(message);
    }
}
