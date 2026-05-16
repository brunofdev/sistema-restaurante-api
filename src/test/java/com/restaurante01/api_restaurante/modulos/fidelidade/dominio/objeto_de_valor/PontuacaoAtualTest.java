package com.restaurante01.api_restaurante.modulos.fidelidade.dominio.objeto_de_valor;

import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.excecao.PontuacaoAtualInvalidaExcecao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PontuacaoAtualTest {

    // ==========================================
    // CRIAÇÃO
    // ==========================================

    @Test
    @DisplayName("Dado zero(), Quando criar, Então deve retornar pontuação com valor 0")
    void deveIniciarComZero() {
        PontuacaoAtual pontuacao = PontuacaoAtual.zero();

        assertThat(pontuacao.valor()).isZero();
    }

    @Test
    @DisplayName("Dado valor nulo, Quando criar, Então deve lançar PontuacaoAtualInvalidaExcecao")
    void naoDeveCriarComValorNulo() {
        assertThatThrownBy(() -> new PontuacaoAtual(null))
                .isInstanceOf(PontuacaoAtualInvalidaExcecao.class);
    }

    @Test
    @DisplayName("Dado valor negativo, Quando criar, Então deve lançar PontuacaoAtualInvalidaExcecao")
    void naoDeveCriarComValorNegativo() {
        assertThatThrownBy(() -> new PontuacaoAtual(-1))
                .isInstanceOf(PontuacaoAtualInvalidaExcecao.class);
    }

    // ==========================================
    // ACRESCENTAR
    // ==========================================

    @Test
    @DisplayName("Dado pontos válidos, Quando acrescentar, Então deve retornar nova instância com saldo somado")
    void deveAcrescentarPontosComSucesso() {
        PontuacaoAtual pontuacao = PontuacaoAtual.zero();

        PontuacaoAtual resultado = pontuacao.acrescentar(10);

        assertThat(resultado.valor()).isEqualTo(10);
    }

    @Test
    @DisplayName("Dado múltiplos acréscimos, Quando acrescentar, Então deve acumular corretamente")
    void deveAcumularPontosEmMultiplosCreditos() {
        PontuacaoAtual pontuacao = PontuacaoAtual.zero()
                .acrescentar(5)
                .acrescentar(3)
                .acrescentar(2);

        assertThat(pontuacao.valor()).isEqualTo(10);
    }

    @Test
    @DisplayName("Dado acréscimo, Quando acrescentar, Então deve retornar nova instância sem mutar a original")
    void deveRetornarNovaInstanciaAoAcrescentar() {
        PontuacaoAtual original = PontuacaoAtual.zero();

        PontuacaoAtual nova = original.acrescentar(5);

        assertThat(original.valor()).isZero();
        assertThat(nova.valor()).isEqualTo(5);
        assertThat(nova).isNotSameAs(original);
    }

    @Test
    @DisplayName("Dado pontos nulos, Quando acrescentar, Então deve lançar PontuacaoAtualInvalidaExcecao")
    void naoDeveAcrescentarPontosNulos() {
        PontuacaoAtual pontuacao = PontuacaoAtual.zero();

        assertThatThrownBy(() -> pontuacao.acrescentar(null))
                .isInstanceOf(PontuacaoAtualInvalidaExcecao.class);
    }

    @Test
    @DisplayName("Dado pontos zero, Quando acrescentar, Então deve lançar PontuacaoAtualInvalidaExcecao")
    void naoDeveAcrescentarPontosZero() {
        PontuacaoAtual pontuacao = PontuacaoAtual.zero();

        assertThatThrownBy(() -> pontuacao.acrescentar(0))
                .isInstanceOf(PontuacaoAtualInvalidaExcecao.class);
    }

    @Test
    @DisplayName("Dado pontos negativos, Quando acrescentar, Então deve lançar PontuacaoAtualInvalidaExcecao")
    void naoDeveAcrescentarPontosNegativos() {
        PontuacaoAtual pontuacao = PontuacaoAtual.zero();

        assertThatThrownBy(() -> pontuacao.acrescentar(-5))
                .isInstanceOf(PontuacaoAtualInvalidaExcecao.class);
    }
}
