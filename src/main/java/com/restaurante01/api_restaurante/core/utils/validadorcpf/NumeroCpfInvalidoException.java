package com.restaurante01.api_restaurante.core.utils.validadorcpf;

public class NumeroCpfInvalidoException extends RuntimeException{
    public NumeroCpfInvalidoException(String message){
        super(message);
    }
}
