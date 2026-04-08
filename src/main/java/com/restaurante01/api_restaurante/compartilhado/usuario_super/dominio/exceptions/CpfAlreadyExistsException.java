package com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions;

public class CpfAlreadyExistsException extends RuntimeException{
    public CpfAlreadyExistsException(String message){
        super(message);
    }
}
