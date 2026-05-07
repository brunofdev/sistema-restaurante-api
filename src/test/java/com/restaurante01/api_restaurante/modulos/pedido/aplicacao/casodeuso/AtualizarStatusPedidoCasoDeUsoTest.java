package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.StatusPedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoCriadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mapeador.PedidoMapeador;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedidoBuilder;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.PedidoBuilder;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCanceladoEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoEntregueEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao.PedidoNaoEncontradoExcecao;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoOutboxPorta;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ItemPedidoPayload;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarStatusPedidoCasoDeUsoTest {

    @Mock private PedidoRepositorio pedidoRepository;
    @Mock private PedidoMapeador pedidoMapeador;
    @Mock private ApplicationEventPublisher publicarEvento;
    @Mock private PedidoOutboxPorta pedidoOutboxPorta;
    @Mock private ObjectMapper objectMapper;

    @InjectMocks
    private AtualizarStatusPedidoCasoDeUso casoDeUso;

    private Pedido pedidoParaEntregar;
    private Pedido pedidoParaCancelar;

    @BeforeEach
    void setUp() {
        pedidoParaEntregar = PedidoBuilder.umPedido().build();
        pedidoParaEntregar.mudarStatus(StatusPedido.EM_PREPARACAO);
        pedidoParaEntregar.mudarStatus(StatusPedido.SAIU_PARA_ENTREGA);

        pedidoParaCancelar = PedidoBuilder.umPedido()
                .comItem(ItemPedidoBuilder.umItemPedido().build())
                .build();
    }

    @Test
    @DisplayName("Deve salvar outbox e publicar evento quando status mudar para ENTREGUE")
    void deveSalvarOutboxEPublicarEventoQuandoStatusEntregue() throws Exception {
        when(pedidoRepository.buscarPorId(any())).thenReturn(Optional.of(pedidoParaEntregar));
        when(pedidoRepository.salvar(any())).thenAnswer(inv -> inv.getArgument(0));
        when(pedidoMapeador.mapearPedidoCriadoDto(any())).thenReturn(Instancio.create(PedidoCriadoDTO.class));

        casoDeUso.executar(1L, new StatusPedidoDTO(StatusPedido.ENTREGUE));

        verify(pedidoOutboxPorta).guardarEvento(
                eq(Agregado.PEDIDO), any(), eq(TipoEvento.COMPUTAR_PONTUACAO_FIDELIDADE), any());
        verify(publicarEvento).publishEvent(any(PedidoEntregueEvento.class));
        verify(pedidoOutboxPorta, times(1)).guardarEvento(any(), any(), any(), any());
    }

    @Test
    @DisplayName("Deve salvar dois eventos no outbox e publicar evento quando status mudar para CANCELADO")
    void deveSalvarOutboxEPublicarEventoQuandoStatusCancelado() throws Exception {
        when(pedidoRepository.buscarPorId(any())).thenReturn(Optional.of(pedidoParaCancelar));
        when(pedidoRepository.salvar(any())).thenAnswer(inv -> inv.getArgument(0));
        when(pedidoMapeador.mapearItemPedidoPayload(anyList())).thenReturn(List.of(new ItemPedidoPayload(1L, 2)));
        when(pedidoMapeador.mapearPedidoCriadoDto(any())).thenReturn(Instancio.create(PedidoCriadoDTO.class));

        casoDeUso.executar(1L, new StatusPedidoDTO(StatusPedido.CANCELADO));

        verify(pedidoOutboxPorta).guardarEvento(
                eq(Agregado.PEDIDO), any(), eq(TipoEvento.ESTORNAR_ESTOQUE_ASSOCIACAO), any());
        verify(pedidoOutboxPorta).guardarEvento(
                eq(Agregado.PEDIDO), any(), eq(TipoEvento.ESTORNAR_ESTOQUE_PRODUTO), any());
        verify(publicarEvento).publishEvent(any(PedidoCanceladoEvento.class));
        verify(pedidoOutboxPorta, times(2)).guardarEvento(any(), any(), any(), any());
    }

    @Test
    @DisplayName("Não deve salvar outbox nem publicar evento quando status não for ENTREGUE ou CANCELADO")
    void naoDeveSalvarOutboxNemPublicarEventoParaStatusNeutro() throws Exception {
        Pedido pedido = PedidoBuilder.umPedido().build();
        when(pedidoRepository.buscarPorId(any())).thenReturn(Optional.of(pedido));
        when(pedidoRepository.salvar(any())).thenAnswer(inv -> inv.getArgument(0));
        when(pedidoMapeador.mapearPedidoCriadoDto(any())).thenReturn(Instancio.create(PedidoCriadoDTO.class));

        casoDeUso.executar(1L, new StatusPedidoDTO(StatusPedido.EM_PREPARACAO));

        verifyNoInteractions(pedidoOutboxPorta);
        verifyNoInteractions(publicarEvento);
    }

    @Test
    @DisplayName("Deve lançar exceção quando pedido não for encontrado")
    void deveLancarExcecaoQuandoPedidoNaoEncontrado() {
        when(pedidoRepository.buscarPorId(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> casoDeUso.executar(99L, new StatusPedidoDTO(StatusPedido.ENTREGUE)))
                .isInstanceOf(PedidoNaoEncontradoExcecao.class);

        verifyNoInteractions(pedidoOutboxPorta);
        verifyNoInteractions(publicarEvento);
    }
}
