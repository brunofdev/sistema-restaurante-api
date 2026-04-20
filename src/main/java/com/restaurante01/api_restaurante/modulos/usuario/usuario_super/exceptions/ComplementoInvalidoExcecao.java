package com.restaurante01.api_restaurante.modulos.usuario.usuario_super.exceptions;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class ComplementoInvalidoExcecao extends RegraDeNegocioExcecao {
    public ComplementoInvalidoExcecao(String message){
        super(message);
    }
}
