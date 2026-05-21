package com.restaurante01.api_restaurante.modulos.fidelidade.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.servico.CalculadoraDeFidelidade;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.entidade.Fidelidade;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.entidade.FidelidadeBuilder;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.excecao.FidelidadeNaoEncontradaExcecao;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.repositorio.FidelidadeRepositorio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessaPontuacaoPedidoEntregueCasoDeUsoTest {

    @Mock
    FidelidadeRepositorio repositorio;

    @InjectMocks
    ProcessaPontuacaoPedidoEntregueCasoDeUso casoDeUso;

    @Test
    @DisplayName("Deve encontrar fidelidade e acrescentar a pontuação calculada")
    void deveEncontrarFidelidadeEAcrescentarPontuacaoCalculada() {
        BigDecimal valorTotalPedido = new BigDecimal(50);
        Fidelidade fidelidade = FidelidadeBuilder.umaFidelidade().construir();
        int pontuacaoEsperada = CalculadoraDeFidelidade.calcular(valorTotalPedido);

        when(repositorio.buscarPorClienteId(1L)).thenReturn(Optional.of(fidelidade));
        casoDeUso.executar(1L, valorTotalPedido);

        assertEquals(pontuacaoEsperada, fidelidade.getPontuacaoAtual().valor());
    }

    @Test
    @DisplayName("Deve salvar a fidelidade com a pontuação atualizada")
    void deveSalvarFidelidadeComNovaPontuacao() {
        BigDecimal valorTotalPedido = new BigDecimal(50);
        Fidelidade fidelidade = FidelidadeBuilder.umaFidelidade().construir();
        int pontuacaoEsperada = CalculadoraDeFidelidade.calcular(valorTotalPedido);

        when(repositorio.buscarPorClienteId(1L)).thenReturn(Optional.of(fidelidade));
        casoDeUso.executar(1L, valorTotalPedido);

        ArgumentCaptor<Fidelidade> captor = ArgumentCaptor.forClass(Fidelidade.class);
        verify(repositorio).salvar(captor.capture());

        assertEquals(pontuacaoEsperada, captor.getValue().getPontuacaoAtual().valor());
    }

    @Test
    @DisplayName("Deve lançar FidelidadeNaoEncontradaExcecao quando fidelidade não encontrada")
    void deveLancarExcecaoQuandoFidelidadeNaoEncontrada() {
        when(repositorio.buscarPorClienteId(99L)).thenReturn(Optional.empty());

        assertThrows(FidelidadeNaoEncontradaExcecao.class,
                () -> casoDeUso.executar(99L, new BigDecimal(50)));
    }
}
