package com.restaurante01.api_restaurante.modulos.fidelidade.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class ClienteIdFidelidadeVazioExcecao extends RegraDeNegocioExcecao {
    public ClienteIdFidelidadeVazioExcecao(String message) {
        super(message);
    }
}
