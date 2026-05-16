package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.saida.AvaliacaoDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.mapeador.AvaliacaoMapeador;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.AvaliacaoBuilder;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.StatusAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.repositorio.AvaliacaoRepositorio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultaAvaliacoesPendentesCasoDeUsoTest {

    @Mock
    private AvaliacaoRepositorio repositorio;

    @Mock
    private AvaliacaoMapeador mapeador;

    @InjectMocks
    private ConsultaAvaliacoesPendentesCasoDeUso casoDeUso;

    @Test
    @DisplayName("Dado que existem avaliações PENDENTE, Quando executar, Então retorna a lista mapeada")
    void deveRetornarAvaliacoesPendentes() {
        Avaliacao avaliacao = AvaliacaoBuilder.umaAvaliacao().construir();
        AvaliacaoDTO dto = mock(AvaliacaoDTO.class);

        when(repositorio.buscarTodasPorStatus(StatusAvaliacao.PENDENTE)).thenReturn(List.of(avaliacao));
        when(mapeador.mapearListaAvaliacoesDTO(List.of(avaliacao))).thenReturn(List.of(dto));

        List<AvaliacaoDTO> resultado = casoDeUso.executar();

        assertThat(resultado).hasSize(1).containsExactly(dto);
    }

    @Test
    @DisplayName("Dado que não há avaliações PENDENTE, Quando executar, Então retorna lista vazia")
    void deveRetornarListaVaziaQuandoNaoHaPendentes() {
        when(repositorio.buscarTodasPorStatus(StatusAvaliacao.PENDENTE)).thenReturn(Collections.emptyList());
        when(mapeador.mapearListaAvaliacoesDTO(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<AvaliacaoDTO> resultado = casoDeUso.executar();

        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Dado qualquer cenário, Quando executar, Então busca somente avaliações com status PENDENTE")
    void deveBuscarApenasAvaliacoesPendentes() {
        when(repositorio.buscarTodasPorStatus(StatusAvaliacao.PENDENTE)).thenReturn(Collections.emptyList());
        when(mapeador.mapearListaAvaliacoesDTO(any())).thenReturn(Collections.emptyList());

        casoDeUso.executar();

        verify(repositorio).buscarTodasPorStatus(StatusAvaliacao.PENDENTE);
        verify(repositorio, never()).buscarTodasPorStatus(StatusAvaliacao.DISPONIVEL);
        verify(repositorio, never()).buscarTodasPorStatus(StatusAvaliacao.CONCLUIDA);
        verify(repositorio, never()).buscarTodasPorStatus(StatusAvaliacao.EXPIRADA);
    }

    @Test
    @DisplayName("Dado qualquer cenário, Quando executar, Então delega o mapeamento ao mapeador")
    void deveDelegarMapeamentoAoMapeador() {
        List<Avaliacao> avaliacoes = List.of(AvaliacaoBuilder.umaAvaliacao().construir());

        when(repositorio.buscarTodasPorStatus(StatusAvaliacao.PENDENTE)).thenReturn(avaliacoes);
        when(mapeador.mapearListaAvaliacoesDTO(avaliacoes)).thenReturn(Collections.emptyList());

        casoDeUso.executar();

        verify(mapeador).mapearListaAvaliacoesDTO(avaliacoes);
    }
}
