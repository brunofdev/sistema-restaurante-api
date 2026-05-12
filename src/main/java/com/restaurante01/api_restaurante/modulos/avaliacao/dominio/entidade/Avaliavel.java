package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.AvaliacaoInvalidaExcecao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.ComentarioAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.NotaAvaliacao;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public class Avaliavel {

    @Embedded
    private NotaAvaliacao nota;
    @Embedded
    private ComentarioAvaliacao comentarioAvaliacao;

    protected void vincularAvaliacao(NotaAvaliacao nota, ComentarioAvaliacao comentarioAvaliacao) {
        if (nota == null && comentarioAvaliacao != null) {
            throw new AvaliacaoInvalidaExcecao("Avaliação obrigatoriamente deve possuir uma Nota.");
        }
        if (nota == null) {
            this.nota = null;
            this.comentarioAvaliacao = null;
            return;
        }
        this.nota = nota;
        this.comentarioAvaliacao = (comentarioAvaliacao != null) ? comentarioAvaliacao : new ComentarioAvaliacao("Avaliação feita sem comentário");
    }
}
