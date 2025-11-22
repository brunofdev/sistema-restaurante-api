package com.restaurante01.api_restaurante.usuarios.exceptions;

public class CpfAlreadyExistsException extends RuntimeException{
    public CpfAlreadyExistsException(String message){
        super(message);
    }
}
