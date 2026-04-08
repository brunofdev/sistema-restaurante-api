package com.restaurante01.api_restaurante.compartilhado.utils.validadorcpf;

public class CpfComTamanhoInvalidoException extends RuntimeException{
    public CpfComTamanhoInvalidoException(String message){
        super(message);
    }
}
