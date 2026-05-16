package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.saida.AvaliacaoDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.mapeador.AvaliacaoMapeador;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.AvaliacaoBuilder;
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
class ConsultaTodasAvaliacoesCasoDeUsoTest {

    @Mock
    private AvaliacaoRepositorio repositorio;

    @Mock
    private AvaliacaoMapeador mapeador;

    @InjectMocks
    private ConsultaTodasAvaliacoesCasoDeUso casoDeUso;

    @Test
    @DisplayName("Dado que existem avaliações, Quando executar, Então retorna a lista mapeada")
    void deveRetornarTodasAvaliacoesMapeadas() {
        List<Avaliacao> avaliacoes = List.of(
                AvaliacaoBuilder.umaAvaliacao().construir(),
                AvaliacaoBuilder.umaAvaliacao().construir()
        );
        AvaliacaoDTO dto = mock(AvaliacaoDTO.class);

        when(repositorio.buscarTodos()).thenReturn(avaliacoes);
        when(mapeador.mapearListaAvaliacoesDTO(avaliacoes)).thenReturn(List.of(dto, dto));

        List<AvaliacaoDTO> resultado = casoDeUso.executar();

        assertThat(resultado).hasSize(2);
    }

    @Test
    @DisplayName("Dado que não existem avaliações, Quando executar, Então retorna lista vazia")
    void deveRetornarListaVaziaQuandoNaoHaAvaliacoes() {
        when(repositorio.buscarTodos()).thenReturn(Collections.emptyList());
        when(mapeador.mapearListaAvaliacoesDTO(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<AvaliacaoDTO> resultado = casoDeUso.executar();

        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Dado qualquer cenário, Quando executar, Então sempre delega o mapeamento ao mapeador")
    void deveSempreDelegarMapeamentoAoMapeador() {
        List<Avaliacao> avaliacoes = List.of(AvaliacaoBuilder.umaAvaliacao().construir());

        when(repositorio.buscarTodos()).thenReturn(avaliacoes);
        when(mapeador.mapearListaAvaliacoesDTO(avaliacoes)).thenReturn(Collections.emptyList());

        casoDeUso.executar();

        verify(mapeador).mapearListaAvaliacoesDTO(avaliacoes);
    }
}
