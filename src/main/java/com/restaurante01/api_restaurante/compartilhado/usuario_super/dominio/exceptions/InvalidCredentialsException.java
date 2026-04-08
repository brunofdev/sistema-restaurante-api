package com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException (String message){
        super(message);
    }
}
