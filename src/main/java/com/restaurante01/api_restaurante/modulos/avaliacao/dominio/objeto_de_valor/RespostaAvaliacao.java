package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.AvaliacaoInvalidaExcecao;

public record RespostaAvaliacao(NotaAvaliacao nota, ComentarioAvaliacao comentarioAvaliacao) {

    public RespostaAvaliacao {
        if (nota == null && comentarioAvaliacao != null) {
            throw new AvaliacaoInvalidaExcecao("Avaliação obrigatoriamente deve possuir uma Nota.");
        }
    }
}
