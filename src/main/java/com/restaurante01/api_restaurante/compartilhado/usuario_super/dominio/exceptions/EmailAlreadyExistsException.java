package com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String message){
        super(message);
    }
}
