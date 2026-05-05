package com.restaurante01.api_restaurante.compartilhado.dominio.excecao;

public class OutboxNaoEncontradoExcecao extends RegraDeNegocioExcecao{
    public OutboxNaoEncontradoExcecao(String message){
        super(message);
    }
}
