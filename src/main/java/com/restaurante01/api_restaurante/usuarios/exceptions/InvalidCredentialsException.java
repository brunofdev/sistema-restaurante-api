package com.restaurante01.api_restaurante.usuarios.exceptions;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException (String message){
        super(message);
    }
}
