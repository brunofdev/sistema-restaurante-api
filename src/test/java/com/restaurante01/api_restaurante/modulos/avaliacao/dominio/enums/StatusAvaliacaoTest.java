package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusAvaliacaoTest {

    @Test
    @DisplayName("Dado o status PENDENTE, Então deve transicionar apenas para DISPONIVEL")
    void transicoesDoPendente() {
        assertThat(StatusAvaliacao.PENDENTE.podeTransicionarPara(StatusAvaliacao.DISPONIVEL)).isTrue();

        assertThat(StatusAvaliacao.PENDENTE.podeTransicionarPara(StatusAvaliacao.PENDENTE)).isFalse();
        assertThat(StatusAvaliacao.PENDENTE.podeTransicionarPara(StatusAvaliacao.CONCLUIDA)).isFalse();
        assertThat(StatusAvaliacao.PENDENTE.podeTransicionarPara(StatusAvaliacao.EXPIRADA)).isFalse();
    }

    @Test
    @DisplayName("Dado o status DISPONIVEL, Então deve transicionar apenas para CONCLUIDA ou EXPIRADA")
    void transicoesDoDisponivel() {
        assertThat(StatusAvaliacao.DISPONIVEL.podeTransicionarPara(StatusAvaliacao.CONCLUIDA)).isTrue();
        assertThat(StatusAvaliacao.DISPONIVEL.podeTransicionarPara(StatusAvaliacao.EXPIRADA)).isTrue();

        assertThat(StatusAvaliacao.DISPONIVEL.podeTransicionarPara(StatusAvaliacao.PENDENTE)).isFalse();
        assertThat(StatusAvaliacao.DISPONIVEL.podeTransicionarPara(StatusAvaliacao.DISPONIVEL)).isFalse();
    }

    @Test
    @DisplayName("Dado os status finais (CONCLUIDA/EXPIRADA), Então não devem transicionar para lugar nenhum")
    void statusFinaisNaoTransicionam() {
        for (StatusAvaliacao novoStatus : StatusAvaliacao.values()) {
            assertThat(StatusAvaliacao.CONCLUIDA.podeTransicionarPara(novoStatus)).isFalse();
            assertThat(StatusAvaliacao.EXPIRADA.podeTransicionarPara(novoStatus)).isFalse();
        }
    }
}