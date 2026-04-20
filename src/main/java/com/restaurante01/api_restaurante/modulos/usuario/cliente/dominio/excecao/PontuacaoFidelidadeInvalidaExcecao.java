package com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class PontuacaoFidelidadeInvalidaExcecao extends RegraDeNegocioExcecao {
    public PontuacaoFidelidadeInvalidaExcecao(String message){
        super(message);
    }
}
