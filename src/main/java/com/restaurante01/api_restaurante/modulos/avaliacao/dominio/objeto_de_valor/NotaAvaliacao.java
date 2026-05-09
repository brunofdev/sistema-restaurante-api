package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.NotaAvaliacaoVazioExcecao;
import jakarta.persistence.Embeddable;

/**
 * Representa a nota atribuída a um restaurante.
 * Analogia: Como um termômetro que só marca de 1 a 5 graus;
 * qualquer valor fora disso é uma leitura impossível para o sistema.
 */

@Embeddable
public record NotaAvaliacao(Integer valor) {

    public NotaAvaliacao {
        if (valor == null || valor < 1 || valor > 5) {
            throw new NotaAvaliacaoVazioExcecao("A nota deve ser um número entre 1 e 5.");
        }
    }

    public static NotaAvaliacao of(Integer valor) {
        return new NotaAvaliacao(valor);
    }
}