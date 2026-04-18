package com.restaurante01.api_restaurante.modulos.cliente.dominio.excecao;

public class ClienteNaoEncontradoExcecao extends RuntimeException{
    public ClienteNaoEncontradoExcecao(String message){
        super(message);
    }
}
