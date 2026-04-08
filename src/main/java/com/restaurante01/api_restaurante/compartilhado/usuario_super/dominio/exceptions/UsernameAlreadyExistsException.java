package com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String message){
        super(message);
    }
}
