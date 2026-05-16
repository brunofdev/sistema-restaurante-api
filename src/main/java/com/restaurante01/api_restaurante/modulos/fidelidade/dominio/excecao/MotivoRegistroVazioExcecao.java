package com.restaurante01.api_restaurante.modulos.fidelidade.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class MotivoRegistroVazioExcecao extends RegraDeNegocioExcecao {
    public MotivoRegistroVazioExcecao(String message) {
        super(message);
    }
}
