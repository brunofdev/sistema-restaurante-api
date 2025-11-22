package com.restaurante01.api_restaurante.usuarios.exceptions;

public class UserDontFoundException extends RuntimeException{
    public UserDontFoundException (String message){
        super(message);
    }
}
