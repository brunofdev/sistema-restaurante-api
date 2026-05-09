package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.ComentarioAvaliacaoVazioExcecao;
import jakarta.persistence.Embeddable;

/**
 * Representa o texto descritivo da avaliação.
 * Analogia: Como um formulário de feedback físico com espaço limitado;
 * se estiver em branco ou transbordar a folha, é rejeitado.
 */
@Embeddable
public record ComentarioAvaliacao(String valor) {

    public ComentarioAvaliacao {
        if (valor == null || valor.isBlank() || valor.length() > 500) {
            throw new ComentarioAvaliacaoVazioExcecao("O comentário não pode ser vazio e deve ter no máximo 500 caracteres.");
        }
    }

    public static ComentarioAvaliacao of(String valor) {
        return new ComentarioAvaliacao(valor);
    }
}