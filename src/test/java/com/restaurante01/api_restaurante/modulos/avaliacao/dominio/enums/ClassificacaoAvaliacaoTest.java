package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.NotaAvaliacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ClassificacaoAvaliacaoTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    @DisplayName("Dado uma nota 1 ou 2, Quando derivar, Então a classificação deve ser INSATISFEITO")
    void deveDerivarInsatisfeito(int valorNota) {
        NotaAvaliacao nota = new NotaAvaliacao(valorNota);
        ClassificacaoAvaliacao classificacao = ClassificacaoAvaliacao.derivarDaNota(nota);

        assertThat(classificacao).isEqualTo(ClassificacaoAvaliacao.INSATISFEITO);
    }

    @ParameterizedTest
    @ValueSource(ints = {3})
    @DisplayName("Dado uma nota 3, Quando derivar, Então a classificação deve ser MODERADO")
    void deveDerivarModerado(int valorNota) {
        NotaAvaliacao nota = new NotaAvaliacao(valorNota);
        ClassificacaoAvaliacao classificacao = ClassificacaoAvaliacao.derivarDaNota(nota);

        assertThat(classificacao).isEqualTo(ClassificacaoAvaliacao.MODERADO);
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 5})
    @DisplayName("Dado uma nota 4 ou 5, Quando derivar, Então a classificação deve ser SATISFEITO")
    void deveDerivarSatisfeito(int valorNota) {
        NotaAvaliacao nota = new NotaAvaliacao(valorNota);
        ClassificacaoAvaliacao classificacao = ClassificacaoAvaliacao.derivarDaNota(nota);

        assertThat(classificacao).isEqualTo(ClassificacaoAvaliacao.SATISFEITO);
    }
}