package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.AvaliacaoInvalidaExcecao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.ComentarioAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.NotaAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.RespostaAvaliacao;
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

    public void vincularAvaliacao(RespostaAvaliacao resposta) {
        if (resposta == null || resposta.nota() == null) {
            if (resposta != null && resposta.comentarioAvaliacao() != null) {
                throw new AvaliacaoInvalidaExcecao("Avaliação obrigatoriamente deve possuir uma Nota.");
            }
            this.nota = null;
            this.comentarioAvaliacao = null;
            return;
        }
        this.nota = resposta.nota();
        this.comentarioAvaliacao = resposta.comentarioAvaliacao() != null
                ? resposta.comentarioAvaliacao()
                : new ComentarioAvaliacao("Avaliação feita sem comentário");
    }
}
