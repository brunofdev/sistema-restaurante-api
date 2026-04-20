package com.restaurante01.api_restaurante.modulos.usuario.usuario_super.exceptions;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class CepInvalidoExcecao extends RegraDeNegocioExcecao {
    public CepInvalidoExcecao(String message){
        super(message);
    }
}
