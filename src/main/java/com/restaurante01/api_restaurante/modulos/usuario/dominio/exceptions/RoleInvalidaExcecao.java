package com.restaurante01.api_restaurante.modulos.usuario.dominio.exceptions;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class RoleInvalidaExcecao extends RegraDeNegocioExcecao {
    public RoleInvalidaExcecao(String message){
        super(message);
    }
}
