package com.restaurante01.api_restaurante.modulos.usuario.usuario_super.exceptions;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class NomeInvalidoExcecao extends RegraDeNegocioExcecao {
    public NomeInvalidoExcecao(String message){
        super(message);
    }
}
