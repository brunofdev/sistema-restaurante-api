package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.AvaliacaoBuilder;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.StatusAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.TentativaNotificacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.AvaliacaoParaNotificar;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.porta.AvaliacaoNotificadorPorta;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.repositorio.AvaliacaoRepositorio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RenotificarClienteCasoDeUsoTest {

    @Mock
    private AvaliacaoRepositorio repositorio;

    @Mock
    private AvaliacaoNotificadorPorta notificador;

    @InjectMocks
    private RenotificarClienteCasoDeUso casoDeUso;

    private Avaliacao disponivel() {
        Avaliacao av = AvaliacaoBuilder.umaAvaliacao().construir();
        ReflectionTestUtils.setField(av, "status", StatusAvaliacao.DISPONIVEL);
        return av;
    }

    private void semPendentes() {
        when(repositorio.buscarPendentesDeRenotificacao(any(), any(), any()))
                .thenReturn(Collections.emptyList());
    }

    @Test
    @DisplayName("Dado que nao ha avaliacoes pendentes, Quando executar, Entao nao deve notificar nenhum cliente")
    void naoDeveNotificarSemAvaliacoesPendentes() {
        semPendentes();

        casoDeUso.executar();

        verify(notificador, never()).notificarCliente(any());
    }

    @Test
    @DisplayName("Dado avaliacao com PRIMEIRA_TENTATIVA ha mais de 3 dias, Quando executar, Entao deve notificar e avançar para SEGUNDA_TENTATIVA")
    void deveRenotificarAvialiacaoNaPrimeiraTentativa() {
        Avaliacao av = disponivel();
        av.foiEnviadaAoCliente(); // → PRIMEIRA_TENTATIVA

        when(repositorio.buscarPendentesDeRenotificacao(
                eq(StatusAvaliacao.DISPONIVEL), eq(TentativaNotificacao.PRIMEIRA_TENTATIVA), any()))
                .thenReturn(List.of(av));
        when(repositorio.buscarPendentesDeRenotificacao(
                eq(StatusAvaliacao.DISPONIVEL), eq(TentativaNotificacao.SEGUNDA_TENTATIVA), any()))
                .thenReturn(Collections.emptyList());

        casoDeUso.executar();

        assertThat(av.getNumeroNotificacaoCliente()).isEqualTo(TentativaNotificacao.SEGUNDA_TENTATIVA);
        verify(notificador).notificarCliente(
                new AvaliacaoParaNotificar(av.getId(), av.getClienteId(), av.getPedidoId()));
    }

    @Test
    @DisplayName("Dado avaliacao com SEGUNDA_TENTATIVA ha mais de 6 dias, Quando executar, Entao deve notificar e avançar para TERCEIRA_TENTATIVA")
    void deveRenotificarAvaliacaoNaSegundaTentativa() {
        Avaliacao av = disponivel();
        av.foiEnviadaAoCliente(); // → PRIMEIRA_TENTATIVA
        av.foiEnviadaAoCliente(); // → SEGUNDA_TENTATIVA

        when(repositorio.buscarPendentesDeRenotificacao(
                eq(StatusAvaliacao.DISPONIVEL), eq(TentativaNotificacao.PRIMEIRA_TENTATIVA), any()))
                .thenReturn(Collections.emptyList());
        when(repositorio.buscarPendentesDeRenotificacao(
                eq(StatusAvaliacao.DISPONIVEL), eq(TentativaNotificacao.SEGUNDA_TENTATIVA), any()))
                .thenReturn(List.of(av));

        casoDeUso.executar();

        assertThat(av.getNumeroNotificacaoCliente()).isEqualTo(TentativaNotificacao.TERCEIRA_TENTATIVA);
        verify(notificador).notificarCliente(
                new AvaliacaoParaNotificar(av.getId(), av.getClienteId(), av.getPedidoId()));
    }

    @Test
    @DisplayName("Dado ambos os grupos com pendencias, Quando executar, Entao deve processar as duas regras na mesma execucao")
    void deveProcessarAmbasAsRegrasPorExecucao() {
        Avaliacao avPrimeira = disponivel();
        avPrimeira.foiEnviadaAoCliente(); // → PRIMEIRA_TENTATIVA

        Avaliacao avSegunda = disponivel();
        avSegunda.foiEnviadaAoCliente(); // → PRIMEIRA_TENTATIVA
        avSegunda.foiEnviadaAoCliente(); // → SEGUNDA_TENTATIVA

        when(repositorio.buscarPendentesDeRenotificacao(
                eq(StatusAvaliacao.DISPONIVEL), eq(TentativaNotificacao.PRIMEIRA_TENTATIVA), any()))
                .thenReturn(List.of(avPrimeira));
        when(repositorio.buscarPendentesDeRenotificacao(
                eq(StatusAvaliacao.DISPONIVEL), eq(TentativaNotificacao.SEGUNDA_TENTATIVA), any()))
                .thenReturn(List.of(avSegunda));

        casoDeUso.executar();

        assertThat(avPrimeira.getNumeroNotificacaoCliente()).isEqualTo(TentativaNotificacao.SEGUNDA_TENTATIVA);
        assertThat(avSegunda.getNumeroNotificacaoCliente()).isEqualTo(TentativaNotificacao.TERCEIRA_TENTATIVA);
        verify(notificador, times(2)).notificarCliente(any());
    }

    @Test
    @DisplayName("Dado que o notificador falha, Quando executar, Entao deve absorver o erro e ainda salvar a lista")
    void deveContinuarEPersistirMesmoComErroDeNotificacao() {
        Avaliacao av = disponivel();
        av.foiEnviadaAoCliente(); // → PRIMEIRA_TENTATIVA

        when(repositorio.buscarPendentesDeRenotificacao(
                eq(StatusAvaliacao.DISPONIVEL), eq(TentativaNotificacao.PRIMEIRA_TENTATIVA), any()))
                .thenReturn(List.of(av));
        when(repositorio.buscarPendentesDeRenotificacao(
                eq(StatusAvaliacao.DISPONIVEL), eq(TentativaNotificacao.SEGUNDA_TENTATIVA), any()))
                .thenReturn(Collections.emptyList());
        doThrow(new RuntimeException("Falha de notificacao")).when(notificador).notificarCliente(any());

        casoDeUso.executar();

        // foiEnviadaAoCliente foi chamado antes do notificador, então a tentativa avançou
        assertThat(av.getNumeroNotificacaoCliente()).isEqualTo(TentativaNotificacao.SEGUNDA_TENTATIVA);
        verify(repositorio, atLeastOnce()).salvarLista(any());
    }

    @Test
    @DisplayName("Dado qualquer cenario, Quando executar, Entao deve buscar PRIMEIRA_TENTATIVA com janela de 3 dias")
    void deveBuscarPrimeiraTentativaComJanelaDe3Dias() {
        ArgumentCaptor<LocalDateTime> horarioCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        semPendentes();

        LocalDateTime antes = LocalDateTime.now();
        casoDeUso.executar();
        LocalDateTime depois = LocalDateTime.now();

        verify(repositorio).buscarPendentesDeRenotificacao(
                eq(StatusAvaliacao.DISPONIVEL),
                eq(TentativaNotificacao.PRIMEIRA_TENTATIVA),
                horarioCaptor.capture());
        assertThat(horarioCaptor.getValue()).isBetween(
                antes.minusDays(3),
                depois.minusDays(3));
    }

    @Test
    @DisplayName("Dado qualquer cenario, Quando executar, Entao deve buscar SEGUNDA_TENTATIVA com janela de 6 dias")
    void deveBuscarSegundaTentativaComJanelaDe6Dias() {
        ArgumentCaptor<LocalDateTime> horarioCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        semPendentes();

        LocalDateTime antes = LocalDateTime.now();
        casoDeUso.executar();
        LocalDateTime depois = LocalDateTime.now();

        verify(repositorio).buscarPendentesDeRenotificacao(
                eq(StatusAvaliacao.DISPONIVEL),
                eq(TentativaNotificacao.SEGUNDA_TENTATIVA),
                horarioCaptor.capture());
        assertThat(horarioCaptor.getValue()).isBetween(
                antes.minusDays(6),
                depois.minusDays(6));
    }
}
