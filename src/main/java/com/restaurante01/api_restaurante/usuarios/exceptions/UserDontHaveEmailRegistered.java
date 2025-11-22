package com.restaurante01.api_restaurante.usuarios.exceptions;

public class UserDontHaveEmailRegistered extends RuntimeException{
    public UserDontHaveEmailRegistered(String message){
        super(message);
    }
}
