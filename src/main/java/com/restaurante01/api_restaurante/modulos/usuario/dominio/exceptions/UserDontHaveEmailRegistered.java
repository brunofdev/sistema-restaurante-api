package com.restaurante01.api_restaurante.modulos.usuario.dominio.exceptions;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class UserDontHaveEmailRegistered extends RegraDeNegocioExcecao {
    public UserDontHaveEmailRegistered(String message){
        super(message);
    }
}
