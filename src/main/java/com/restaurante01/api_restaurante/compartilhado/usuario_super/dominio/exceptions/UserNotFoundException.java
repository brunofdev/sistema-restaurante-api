package com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }
}
