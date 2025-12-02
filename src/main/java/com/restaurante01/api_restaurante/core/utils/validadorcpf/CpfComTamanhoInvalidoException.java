package com.restaurante01.api_restaurante.core.utils.validadorcpf;

public class CpfComTamanhoInvalidoException extends RuntimeException{
    public CpfComTamanhoInvalidoException(String message){
        super(message);
    }
}
