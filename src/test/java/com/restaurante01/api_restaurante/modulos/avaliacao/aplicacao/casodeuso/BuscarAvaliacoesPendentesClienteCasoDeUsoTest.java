package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.saida.AvaliacaoPendenteClienteDTO;
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
class BuscarAvaliacoesPendentesClienteCasoDeUsoTest {

    @Mock
    private AvaliacaoRepositorio repositorio;

    @Mock
    private AvaliacaoMapeador mapeador;

    @InjectMocks
    private BuscarAvaliacoesPendentesClienteCasoDeUso casoDeUso;

    private static final Long CLIENTE_ID = 200L;

    @Test
    @DisplayName("Dado um cliente com avaliações disponíveis, Quando executar, Então retorna a lista mapeada")
    void deveRetornarAvaliacoesPendentesDoCliente() {
        Avaliacao avaliacao = AvaliacaoBuilder.umaAvaliacao().construir();
        AvaliacaoPendenteClienteDTO dto = mock(AvaliacaoPendenteClienteDTO.class);

        when(repositorio.buscarAvaliacoesPorClienteId(StatusAvaliacao.DISPONIVEL, CLIENTE_ID))
                .thenReturn(List.of(avaliacao));
        when(mapeador.mapearAvaliacoesPendentesDoCliente(List.of(avaliacao)))
                .thenReturn(List.of(dto));

        List<AvaliacaoPendenteClienteDTO> resultado = casoDeUso.executar(CLIENTE_ID);

        assertThat(resultado).hasSize(1).containsExactly(dto);
    }

    @Test
    @DisplayName("Dado um cliente sem avaliações disponíveis, Quando executar, Então retorna lista vazia")
    void deveRetornarListaVaziaQuandoNaoHaAvaliacoes() {
        when(repositorio.buscarAvaliacoesPorClienteId(StatusAvaliacao.DISPONIVEL, CLIENTE_ID))
                .thenReturn(Collections.emptyList());
        when(mapeador.mapearAvaliacoesPendentesDoCliente(Collections.emptyList()))
                .thenReturn(Collections.emptyList());

        List<AvaliacaoPendenteClienteDTO> resultado = casoDeUso.executar(CLIENTE_ID);

        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Dado qualquer cliente, Quando executar, Então busca somente avaliações com status DISPONIVEL")
    void deveBuscarApenasAvaliacoesDisponiveis() {
        when(repositorio.buscarAvaliacoesPorClienteId(any(), any()))
                .thenReturn(Collections.emptyList());
        when(mapeador.mapearAvaliacoesPendentesDoCliente(any()))
                .thenReturn(Collections.emptyList());

        casoDeUso.executar(CLIENTE_ID);

        verify(repositorio).buscarAvaliacoesPorClienteId(StatusAvaliacao.DISPONIVEL, CLIENTE_ID);
        verify(repositorio, never()).buscarAvaliacoesPorClienteId(eq(StatusAvaliacao.PENDENTE), any());
        verify(repositorio, never()).buscarAvaliacoesPorClienteId(eq(StatusAvaliacao.CONCLUIDA), any());
        verify(repositorio, never()).buscarAvaliacoesPorClienteId(eq(StatusAvaliacao.EXPIRADA), any());
    }

    @Test
    @DisplayName("Dado qualquer cenário, Quando executar, Então sempre delega o mapeamento ao mapeador")
    void deveSempreDelegarMapeamentoAoMapeador() {
        List<Avaliacao> avaliacoes = List.of(
                AvaliacaoBuilder.umaAvaliacao().construir(),
                AvaliacaoBuilder.umaAvaliacao().construir()
        );

        when(repositorio.buscarAvaliacoesPorClienteId(StatusAvaliacao.DISPONIVEL, CLIENTE_ID))
                .thenReturn(avaliacoes);
        when(mapeador.mapearAvaliacoesPendentesDoCliente(avaliacoes))
                .thenReturn(Collections.emptyList());

        casoDeUso.executar(CLIENTE_ID);

        verify(mapeador).mapearAvaliacoesPendentesDoCliente(avaliacoes);
    }
}
