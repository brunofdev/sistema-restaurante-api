package com.restaurante01.api_restaurante.modulos.fidelidade.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class PontuacaoAtualInvalidaExcecao extends RegraDeNegocioExcecao {
    public PontuacaoAtualInvalidaExcecao(String message) {
        super(message);
    }
}
