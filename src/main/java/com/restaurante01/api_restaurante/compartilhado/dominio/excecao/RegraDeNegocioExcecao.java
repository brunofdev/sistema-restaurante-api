package com.restaurante01.api_restaurante.compartilhado.dominio.excecao;

public class RegraDeNegocioExcecao extends RuntimeException{
    public RegraDeNegocioExcecao(String message){
        super(message);
    }
}
