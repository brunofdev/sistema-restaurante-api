package com.restaurante01.api_restaurante.modulos.cliente.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class ClienteNaoEncontradoExcecao extends RegraDeNegocioExcecao {
    public ClienteNaoEncontradoExcecao(String message){
        super(message);
    }
}
