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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DisponibilizarAvaliacaoCasoDeUsoTest {

    @Mock
    private AvaliacaoRepositorio repositorio;

    @Mock
    private AvaliacaoNotificadorPorta notificadorPorta;

    @InjectMocks
    private DisponibilizarAvaliacaoCasoDeUso casoDeUso;

    @Test
    @DisplayName("Dado que nao ha avaliacoes PENDENTE antigas, Quando executar, Entao retorna sem salvar nem notificar")
    void naoDeveFazerNadaSeListaVazia() {
        when(repositorio.buscarTodasCriadasAte(eq(StatusAvaliacao.PENDENTE), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        casoDeUso.executar();

        verify(notificadorPorta, never()).notificarCliente(any());
        verify(repositorio, never()).salvarLista(any());
    }

    @Test
    @DisplayName("Dado uma avaliacao PENDENTE criada ha mais de 1 hora, Quando executar, Entao deve mudar status para DISPONIVEL e notificar")
    void deveDisponibilizarENotificarAvaliacao() {
        Avaliacao avaliacao = AvaliacaoBuilder.umaAvaliacao().construir();

        when(repositorio.buscarTodasCriadasAte(eq(StatusAvaliacao.PENDENTE), any(LocalDateTime.class)))
                .thenReturn(List.of(avaliacao));

        casoDeUso.executar();

        assertThat(avaliacao.getStatus()).isEqualTo(StatusAvaliacao.DISPONIVEL);
        verify(notificadorPorta).notificarCliente(
                new AvaliacaoParaNotificar(avaliacao.getId(), avaliacao.getClienteId(), avaliacao.getPedidoId()));
        verify(repositorio).salvarLista(List.of(avaliacao));
    }

    @Test
    @DisplayName("Dado uma avaliacao PENDENTE, Quando disponibilizada, Entao deve registrar como PRIMEIRA_TENTATIVA de notificacao")
    void deveRegistrarPrimeiraNotificacaoAoDisponibilizar() {
        Avaliacao avaliacao = AvaliacaoBuilder.umaAvaliacao().construir();

        when(repositorio.buscarTodasCriadasAte(eq(StatusAvaliacao.PENDENTE), any(LocalDateTime.class)))
                .thenReturn(List.of(avaliacao));

        casoDeUso.executar();

        assertThat(avaliacao.getNumeroNotificacaoCliente()).isEqualTo(TentativaNotificacao.PRIMEIRA_TENTATIVA);
    }

    @Test
    @DisplayName("Dado que o notificador falha, Quando executar, Entao deve absorver o erro e ainda salvar a lista")
    void deveContinuarMesmoComErroDeNotificacao() {
        Avaliacao avaliacao = AvaliacaoBuilder.umaAvaliacao().construir();

        when(repositorio.buscarTodasCriadasAte(eq(StatusAvaliacao.PENDENTE), any(LocalDateTime.class)))
                .thenReturn(List.of(avaliacao));
        doThrow(new RuntimeException("Falha de notificacao")).when(notificadorPorta).notificarCliente(any());

        casoDeUso.executar();

        assertThat(avaliacao.getStatus()).isEqualTo(StatusAvaliacao.DISPONIVEL);
        assertThat(avaliacao.getNumeroNotificacaoCliente()).isEqualTo(TentativaNotificacao.PRIMEIRA_TENTATIVA);
        verify(repositorio).salvarLista(List.of(avaliacao));
    }

    @Test
    @DisplayName("Dado qualquer cenario, Quando executar, Entao deve buscar avaliacoes criadas ha mais de 1 hora")
    void deveBuscarComLimiteDeUmaHora() {
        ArgumentCaptor<LocalDateTime> horarioCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        when(repositorio.buscarTodasCriadasAte(eq(StatusAvaliacao.PENDENTE), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        LocalDateTime antes = LocalDateTime.now();
        casoDeUso.executar();
        LocalDateTime depois = LocalDateTime.now();

        verify(repositorio).buscarTodasCriadasAte(eq(StatusAvaliacao.PENDENTE), horarioCaptor.capture());
        assertThat(horarioCaptor.getValue()).isBetween(
                antes.minusHours(1),
                depois.minusHours(1));
    }
}
