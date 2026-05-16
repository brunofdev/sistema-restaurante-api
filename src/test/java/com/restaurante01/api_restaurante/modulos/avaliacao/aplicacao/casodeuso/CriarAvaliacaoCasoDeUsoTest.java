package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.mapeador.AvaliacaoMapeador;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.AvaliacaoItem;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.RespostaAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.ItemAvaliadoVazioExcecao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.repositorio.AvaliacaoRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ItemPedidoAvaliacaoPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarAvaliacaoCasoDeUsoTest {

    @Mock
    private AvaliacaoRepositorio repositorio;

    @Mock
    private AvaliacaoMapeador mapeador;

    @InjectMocks
    private CriarAvaliacaoCasoDeUso casoDeUso;

    @Captor
    private ArgumentCaptor<Avaliacao> avaliacaoCaptor;

    @Test
    @DisplayName("Dado um pedido valido, Quando executar, Entao deve mapear itens e salvar a avaliacao")
    void deveCriarESalvarAvaliacaoComSucesso() {
        Long pedidoId = 123L;
        Long clienteId = 456L;

        List<ItemPedidoAvaliacaoPayload> itensPayload = List.of(mock(ItemPedidoAvaliacaoPayload.class));

        List<AvaliacaoItem> itensMapeados = List.of(AvaliacaoItem.criar(1L, "Pizza", new RespostaAvaliacao(null, null)));

        when(mapeador.mapearItensPedido(itensPayload)).thenReturn(itensMapeados);

        casoDeUso.executar(pedidoId, clienteId, itensPayload);

        verify(mapeador).mapearItensPedido(itensPayload);
        verify(repositorio).salvar(avaliacaoCaptor.capture());

        Avaliacao avaliacaoSalva = avaliacaoCaptor.getValue();
        assertThat(avaliacaoSalva.getPedidoId()).isEqualTo(pedidoId);
        assertThat(avaliacaoSalva.getClienteId()).isEqualTo(clienteId);
        assertThat(avaliacaoSalva.getItensAvaliados()).hasSize(1);
    }

    @Test
    @DisplayName("Dado que a lista mapeada retorna vazia, Quando executar, Entao lança erro do dominio e nao salva no banco")
    void naoDeveSalvarSeNaoHouverItens() {
        Long pedidoId = 123L;
        Long clienteId = 456L;

        List<ItemPedidoAvaliacaoPayload> itensPayload = List.of();

        when(mapeador.mapearItensPedido(itensPayload)).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> casoDeUso.executar(pedidoId, clienteId, itensPayload))
                .isInstanceOf(ItemAvaliadoVazioExcecao.class);

        verify(repositorio, never()).salvar(any());
    }
}