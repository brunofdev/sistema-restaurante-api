package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.LimiteNotificacoesAtingidoExcecao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TentativaNotificacaoTest {

    @Test
    @DisplayName("Dado a primeira tentativa, Quando pedir a próxima, Então retorna a segunda")
    void deveAvancarDaPrimeiraParaSegunda() {
        TentativaNotificacao atual = TentativaNotificacao.PRIMEIRA_TENTATIVA;

        assertThat(atual.proxima()).isEqualTo(TentativaNotificacao.SEGUNDA_TENTATIVA);
        assertThat(atual.isUltimaTentativa()).isFalse();
    }

    @Test
    @DisplayName("Dado a segunda tentativa, Quando pedir a próxima, Então retorna a terceira e marca como última")
    void deveAvancarDaSegundaParaTerceira() {
        TentativaNotificacao atual = TentativaNotificacao.SEGUNDA_TENTATIVA;
        TentativaNotificacao proxima = atual.proxima();

        assertThat(proxima).isEqualTo(TentativaNotificacao.TERCEIRA_TENTATIVA);
        assertThat(proxima.isUltimaTentativa()).isTrue();
    }

    @Test
    @DisplayName("Dado a terceira tentativa (última), Quando pedir a próxima, Então lança exceção de limite")
    void deveLancarExcecaoAoUltrapassarLimite() {
        TentativaNotificacao atual = TentativaNotificacao.TERCEIRA_TENTATIVA;

        assertThatThrownBy(atual::proxima)
                .isInstanceOf(LimiteNotificacoesAtingidoExcecao.class)
                .hasMessage("Limite máximo de notificações atingido.");
    }
}