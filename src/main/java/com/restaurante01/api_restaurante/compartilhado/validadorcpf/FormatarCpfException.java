package com.restaurante01.api_restaurante.compartilhado.validadorcpf;

public class FormatarCpfException extends RuntimeException{
    public FormatarCpfException(String message){
        super(message);
    }
}
