package com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions;

public class UserDontFoundException extends RuntimeException{
    public UserDontFoundException (String message){
        super(message);
    }
}
