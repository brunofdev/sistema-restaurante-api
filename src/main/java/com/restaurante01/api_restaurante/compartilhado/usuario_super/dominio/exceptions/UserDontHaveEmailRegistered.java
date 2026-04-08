package com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions;

public class UserDontHaveEmailRegistered extends RuntimeException{
    public UserDontHaveEmailRegistered(String message){
        super(message);
    }
}
