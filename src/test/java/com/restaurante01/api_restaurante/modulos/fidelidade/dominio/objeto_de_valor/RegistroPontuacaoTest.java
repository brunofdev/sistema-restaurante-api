package com.restaurante01.api_restaurante.modulos.fidelidade.dominio.objeto_de_valor;

import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.excecao.MotivoRegistroVazioExcecao;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.excecao.PontuacaoAtualInvalidaExcecao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RegistroPontuacaoTest {

    // ==========================================
    // CRIAÇÃO VIA FACTORY METHOD
    // ==========================================

    @Test
    @DisplayName("Dado dados válidos, Quando criar(), Então deve instanciar com os valores informados")
    void deveCriarRegistroComSucesso() {
        RegistroPontuacao registro = RegistroPontuacao.criar(10, "Avaliação concluída", 10);

        assertThat(registro.ultimaPontuacaoGerada()).isEqualTo(10);
        assertThat(registro.motivo()).isEqualTo("Avaliação concluída");
        assertThat(registro.saldoNoDia()).isEqualTo(10);
    }

    @Test
    @DisplayName("Dado criar(), Quando instanciar, Então deve registrar a data de computação automaticamente")
    void deveCriarComDataComputacaoPreenchida() {
        LocalDateTime antes = LocalDateTime.now().minusSeconds(1);

        RegistroPontuacao registro = RegistroPontuacao.criar(5, "Motivo", 5);

        assertThat(registro.dataComputacao()).isAfterOrEqualTo(antes);
    }

    // ==========================================
    // VALIDAÇÕES — ultimaPontuacaoGerada
    // ==========================================

    @Test
    @DisplayName("Dado pontuação nula, Quando criar, Então deve lançar PontuacaoAtualInvalidaExcecao")
    void naoDeveCriarComPontuacaoNula() {
        assertThatThrownBy(() -> new RegistroPontuacao(null, "Motivo", LocalDateTime.now(), 0))
                .isInstanceOf(PontuacaoAtualInvalidaExcecao.class);
    }

    @Test
    @DisplayName("Dado pontuação zero, Quando criar, Então deve lançar PontuacaoAtualInvalidaExcecao")
    void naoDeveCriarComPontuacaoZero() {
        assertThatThrownBy(() -> new RegistroPontuacao(0, "Motivo", LocalDateTime.now(), 0))
                .isInstanceOf(PontuacaoAtualInvalidaExcecao.class);
    }

    @Test
    @DisplayName("Dado pontuação negativa, Quando criar, Então deve lançar PontuacaoAtualInvalidaExcecao")
    void naoDeveCriarComPontuacaoNegativa() {
        assertThatThrownBy(() -> new RegistroPontuacao(-3, "Motivo", LocalDateTime.now(), 0))
                .isInstanceOf(PontuacaoAtualInvalidaExcecao.class);
    }

    // ==========================================
    // VALIDAÇÕES — motivo
    // ==========================================

    @Test
    @DisplayName("Dado motivo nulo, Quando criar, Então deve lançar MotivoRegistroVazioExcecao")
    void naoDeveCriarComMotivoNulo() {
        assertThatThrownBy(() -> new RegistroPontuacao(5, null, LocalDateTime.now(), 5))
                .isInstanceOf(MotivoRegistroVazioExcecao.class);
    }

    @Test
    @DisplayName("Dado motivo vazio, Quando criar, Então deve lançar MotivoRegistroVazioExcecao")
    void naoDeveCriarComMotivoVazio() {
        assertThatThrownBy(() -> new RegistroPontuacao(5, "  ", LocalDateTime.now(), 5))
                .isInstanceOf(MotivoRegistroVazioExcecao.class);
    }

    // ==========================================
    // VALIDAÇÕES — dataComputacao
    // ==========================================

    @Test
    @DisplayName("Dado data de computação nula, Quando criar, Então deve lançar PontuacaoAtualInvalidaExcecao")
    void naoDeveCriarComDataComputacaoNula() {
        assertThatThrownBy(() -> new RegistroPontuacao(5, "Motivo", null, 5))
                .isInstanceOf(PontuacaoAtualInvalidaExcecao.class);
    }

    // ==========================================
    // VALIDAÇÕES — saldoNoDia
    // ==========================================

    @Test
    @DisplayName("Dado saldo nulo, Quando criar, Então deve lançar PontuacaoAtualInvalidaExcecao")
    void naoDeveCriarComSaldoNulo() {
        assertThatThrownBy(() -> new RegistroPontuacao(5, "Motivo", LocalDateTime.now(), null))
                .isInstanceOf(PontuacaoAtualInvalidaExcecao.class);
    }

    @Test
    @DisplayName("Dado saldo negativo, Quando criar, Então deve lançar PontuacaoAtualInvalidaExcecao")
    void naoDeveCriarComSaldoNegativo() {
        assertThatThrownBy(() -> new RegistroPontuacao(5, "Motivo", LocalDateTime.now(), -1))
                .isInstanceOf(PontuacaoAtualInvalidaExcecao.class);
    }
}
