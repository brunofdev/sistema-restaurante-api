package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.NotaAvaliacaoVazioExcecao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NotaAvaliacaoTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("Dado valores entre 1 e 5, Quando criar, Então instancia corretamente")
    void deveCriarComValoresValidos(int valor) {
        NotaAvaliacao nota = new NotaAvaliacao(valor);

        assertThat(nota.valor()).isEqualTo(valor);
    }

    @Test
    @DisplayName("Dado um valor válido, Quando usar factory of(), Então instancia igual ao construtor")
    void deveCriarViaFactoryMethod() {
        NotaAvaliacao nota = NotaAvaliacao.of(4);

        assertThat(nota.valor()).isEqualTo(4);
    }

    @Test
    @DisplayName("Dado valor nulo, Quando criar, Então lança exceção")
    void naoDeveCriarComValorNulo() {
        assertThatThrownBy(() -> new NotaAvaliacao(null))
                .isInstanceOf(NotaAvaliacaoVazioExcecao.class)
                .hasMessage("A nota deve ser um número entre 1 e 5.");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    @DisplayName("Dado valores menores que 1, Quando criar, Então lança exceção")
    void naoDeveCriarComValorAbaixoDoMinimo(int valor) {
        assertThatThrownBy(() -> new NotaAvaliacao(valor))
                .isInstanceOf(NotaAvaliacaoVazioExcecao.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 10, 100})
    @DisplayName("Dado valores maiores que 5, Quando criar, Então lança exceção")
    void naoDeveCriarComValorAcimaDoMaximo(int valor) {
        assertThatThrownBy(() -> new NotaAvaliacao(valor))
                .isInstanceOf(NotaAvaliacaoVazioExcecao.class);
    }
}
