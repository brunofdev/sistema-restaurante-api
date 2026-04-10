package com.restaurante01.api_restaurante.compartilhado.dominio.excecao;

public class BusinessException extends RuntimeException{
    public BusinessException (String message){
        super(message);
    }
}
