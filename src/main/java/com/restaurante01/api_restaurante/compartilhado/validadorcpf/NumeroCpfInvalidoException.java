package com.restaurante01.api_restaurante.compartilhado.validadorcpf;

public class NumeroCpfInvalidoException extends RuntimeException{
    public NumeroCpfInvalidoException(String message){
        super(message);
    }
}
