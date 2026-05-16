package com.restaurante01.api_restaurante.modulos.fidelidade.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.excecao.ClienteIdFidelidadeVazioExcecao;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.excecao.MotivoRegistroVazioExcecao;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.objeto_de_valor.RegistroPontuacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FidelidadeTest {

    // ==========================================
    // CRIAÇÃO
    // ==========================================

    @Test
    @DisplayName("Dado clienteId válido, Quando criar, Então deve iniciar com pontuação zero e histórico vazio")
    void deveCriarComPontuacaoZeroEHistoricoVazio() {
        Fidelidade fidelidade = FidelidadeBuilder.umaFidelidade().construir();

        assertThat(fidelidade.getPontuacaoAtual().valor()).isZero();
        assertThat(fidelidade.getHistorico()).isEmpty();
    }

    @Test
    @DisplayName("Dado clienteId válido, Quando criar, Então deve vincular o clienteId informado")
    void deveCriarComClienteIdVinculado() {
        Fidelidade fidelidade = FidelidadeBuilder.umaFidelidade().construir();

        assertThat(fidelidade.getClienteId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Dado clienteId nulo, Quando criar, Então deve lançar ClienteIdFidelidadeVazioExcecao")
    void naoDeveCriarSemClienteId() {
        assertThatThrownBy(() -> FidelidadeBuilder.umaFidelidade().semCliente().construir())
                .isInstanceOf(ClienteIdFidelidadeVazioExcecao.class);
    }

    // ==========================================
    // CREDITAR PONTOS
    // ==========================================

    @Test
    @DisplayName("Dado pontos e motivo válidos, Quando creditarPontos, Então deve atualizar a pontuação atual")
    void deveCreditarPontosAtualizandoSaldo() {
        Fidelidade fidelidade = FidelidadeBuilder.umaFidelidade().construir();

        fidelidade.creditarPontos(10, "Avaliação concluída");

        assertThat(fidelidade.getPontuacaoAtual().valor()).isEqualTo(10);
    }

    @Test
    @DisplayName("Dado pontos válidos, Quando creditarPontos, Então deve adicionar um registro ao histórico")
    void deveCreditarPontosAdicionandoRegistroAoHistorico() {
        Fidelidade fidelidade = FidelidadeBuilder.umaFidelidade().construir();

        fidelidade.creditarPontos(5, "Avaliação concluída");

        assertThat(fidelidade.getHistorico()).hasSize(1);
    }

    @Test
    @DisplayName("Dado múltiplos créditos, Quando creditarPontos, Então deve acumular saldo e histórico corretamente")
    void deveCreditarPontosMultiplasVezes() {
        Fidelidade fidelidade = FidelidadeBuilder.umaFidelidade().construir();

        fidelidade.creditarPontos(5, "Primeira avaliação");
        fidelidade.creditarPontos(3, "Segunda avaliação");

        assertThat(fidelidade.getPontuacaoAtual().valor()).isEqualTo(8);
        assertThat(fidelidade.getHistorico()).hasSize(2);
    }

    @Test
    @DisplayName("Dado crédito, Quando creditarPontos, Então o saldoNoDia do registro deve refletir o saldo pós-crédito")
    void deveRegistrarSaldoCorretoNoHistorico() {
        Fidelidade fidelidade = FidelidadeBuilder.umaFidelidade().construir();

        fidelidade.creditarPontos(10, "Avaliação concluída");

        RegistroPontuacao registro = fidelidade.getHistorico().get(0);
        assertThat(registro.saldoNoDia()).isEqualTo(10);
        assertThat(registro.ultimaPontuacaoGerada()).isEqualTo(10);
    }

    @Test
    @DisplayName("Dado crédito, Quando creditarPontos, Então deve registrar o motivo informado no histórico")
    void deveRegistrarMotivoNoHistorico() {
        Fidelidade fidelidade = FidelidadeBuilder.umaFidelidade().construir();

        fidelidade.creditarPontos(5, "Avaliação concluída — pedido #42");

        assertThat(fidelidade.getHistorico().get(0).motivo())
                .isEqualTo("Avaliação concluída — pedido #42");
    }

    @Test
    @DisplayName("Dado motivo nulo, Quando creditarPontos, Então deve lançar MotivoRegistroVazioExcecao")
    void naoDeveCreditarComMotivoNulo() {
        Fidelidade fidelidade = FidelidadeBuilder.umaFidelidade().construir();

        assertThatThrownBy(() -> fidelidade.creditarPontos(5, null))
                .isInstanceOf(MotivoRegistroVazioExcecao.class);
    }

    @Test
    @DisplayName("Dado motivo vazio, Quando creditarPontos, Então deve lançar MotivoRegistroVazioExcecao")
    void naoDeveCreditarComMotivoVazio() {
        Fidelidade fidelidade = FidelidadeBuilder.umaFidelidade().construir();

        assertThatThrownBy(() -> fidelidade.creditarPontos(5, "  "))
                .isInstanceOf(MotivoRegistroVazioExcecao.class);
    }

    // ==========================================
    // IMUTABILIDADE DO HISTÓRICO
    // ==========================================

    @Test
    @DisplayName("Dado histórico com registros, Quando getHistorico, Então deve retornar lista não modificável")
    void deveRetornarHistoricoNaoModificavel() {
        Fidelidade fidelidade = FidelidadeBuilder.umaFidelidade().construir();
        fidelidade.creditarPontos(5, "Avaliação concluída");

        List<RegistroPontuacao> historico = fidelidade.getHistorico();

        assertThatThrownBy(() -> historico.add(RegistroPontuacao.criar(1, "Tentativa externa", 6)))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
