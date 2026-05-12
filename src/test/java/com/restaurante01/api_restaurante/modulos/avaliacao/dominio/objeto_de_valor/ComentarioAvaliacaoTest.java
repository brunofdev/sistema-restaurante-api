package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.ComentarioAvaliacaoVazioExcecao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ComentarioAvaliacaoTest {

    @Test
    @DisplayName("Dado um texto válido, Quando criar, Então instancia corretamente")
    void deveCriarComSucesso() {
        ComentarioAvaliacao comentario = new ComentarioAvaliacao("Ótimo atendimento!");

        assertThat(comentario.valor()).isEqualTo("Ótimo atendimento!");
    }

    @Test
    @DisplayName("Dado um texto válido, Quando usar factory of(), Então instancia igual ao construtor")
    void deveCriarViaFactoryMethod() {
        ComentarioAvaliacao comentario = ComentarioAvaliacao.of("Chegou rápido e quentinho.");

        assertThat(comentario.valor()).isEqualTo("Chegou rápido e quentinho.");
    }

    @Test
    @DisplayName("Dado um valor nulo, Quando criar, Então lança exceção")
    void naoDeveCriarComValorNulo() {
        assertThatThrownBy(() -> new ComentarioAvaliacao(null))
                .isInstanceOf(ComentarioAvaliacaoVazioExcecao.class)
                .hasMessage("O comentário não pode ser vazio e deve ter no máximo 500 caracteres.");
    }

    @Test
    @DisplayName("Dado um valor em branco, Quando criar, Então lança exceção")
    void naoDeveCriarComValorEmBranco() {
        assertThatThrownBy(() -> new ComentarioAvaliacao("   "))
                .isInstanceOf(ComentarioAvaliacaoVazioExcecao.class);
    }

    @Test
    @DisplayName("Dado uma string vazia, Quando criar, Então lança exceção")
    void naoDeveCriarComStringVazia() {
        assertThatThrownBy(() -> new ComentarioAvaliacao(""))
                .isInstanceOf(ComentarioAvaliacaoVazioExcecao.class);
    }

    @Test
    @DisplayName("Dado um texto com exatamente 500 caracteres, Quando criar, Então aceita")
    void deveCriarComExatamente500Caracteres() {
        String texto = "A".repeat(500);

        ComentarioAvaliacao comentario = new ComentarioAvaliacao(texto);

        assertThat(comentario.valor()).hasSize(500);
    }

    @Test
    @DisplayName("Dado um texto com 501 caracteres, Quando criar, Então lança exceção")
    void naoDeveCriarComMaisDe500Caracteres() {
        String texto = "A".repeat(501);

        assertThatThrownBy(() -> new ComentarioAvaliacao(texto))
                .isInstanceOf(ComentarioAvaliacaoVazioExcecao.class);
    }
}
