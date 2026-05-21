package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.schuduler;

import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoCriadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mapeador.PedidoMapeador;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.PedidoBuilder;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrganizaPedidosPorStatusHandlerTest {

    @Mock private PedidoRepositorio repositorio;
    @Mock private SimpMessagingTemplate messagingTemplate;
    @Mock private PedidoMapeador mapeador;

    @InjectMocks
    private OrganizaPedidosPorStatusHandler handler;

    private Pedido pedidoPendente;
    private Pedido pedidoEmPreparacao;
    private Pedido pedidoEmEntrega;
    private Pedido pedidoCancelado;
    private Pedido pedidoEntregue;

    @BeforeEach
    void setUp() {
        pedidoPendente = PedidoBuilder.umPedido().build();

        pedidoEmPreparacao = PedidoBuilder.umPedido().build();
        pedidoEmPreparacao.mudarStatus(StatusPedido.EM_PREPARACAO);

        pedidoEmEntrega = PedidoBuilder.umPedido().build();
        pedidoEmEntrega.mudarStatus(StatusPedido.EM_PREPARACAO);
        pedidoEmEntrega.mudarStatus(StatusPedido.SAIU_PARA_ENTREGA);

        pedidoCancelado = PedidoBuilder.umPedido().build();
        pedidoCancelado.mudarStatus(StatusPedido.CANCELADO);

        pedidoEntregue = PedidoBuilder.umPedido().build();
        pedidoEntregue.mudarStatus(StatusPedido.EM_PREPARACAO);
        pedidoEntregue.mudarStatus(StatusPedido.SAIU_PARA_ENTREGA);
        pedidoEntregue.mudarStatus(StatusPedido.ENTREGUE);
    }

    @Test
    @DisplayName("Deve enviar pedido PENDENTE para o tópico /topico/pedidos/pendentes")
    void deveEnviarPedidoPendenteParaTopicoCorreto() {
        PedidoCriadoDTO dto = Instancio.create(PedidoCriadoDTO.class);
        when(repositorio.buscarParaScheduler()).thenReturn(List.of(pedidoPendente));
        when(mapeador.mapearPedidoCriadoDto(pedidoPendente)).thenReturn(dto);

        handler.executar();

        verify(messagingTemplate).convertAndSend("/topico/pedidos/pendentes", dto);
        verify(messagingTemplate, times(1)).convertAndSend(anyString(), any(PedidoCriadoDTO.class));
    }

    @Test
    @DisplayName("Deve enviar pedido EM_PREPARACAO para o tópico /topico/pedidos/em-preparacao")
    void deveEnviarPedidoEmPreparacaoParaTopicoCorreto() {
        PedidoCriadoDTO dto = Instancio.create(PedidoCriadoDTO.class);
        when(repositorio.buscarParaScheduler()).thenReturn(List.of(pedidoEmPreparacao));
        when(mapeador.mapearPedidoCriadoDto(pedidoEmPreparacao)).thenReturn(dto);

        handler.executar();

        verify(messagingTemplate).convertAndSend("/topico/pedidos/em-preparacao", dto);
        verify(messagingTemplate, times(1)).convertAndSend(anyString(), any(PedidoCriadoDTO.class));
    }

    @Test
    @DisplayName("Deve enviar pedido SAIU_PARA_ENTREGA para o tópico /topico/pedidos/em-entrega")
    void deveEnviarPedidoEmEntregaParaTopicoCorreto() {
        PedidoCriadoDTO dto = Instancio.create(PedidoCriadoDTO.class);
        when(repositorio.buscarParaScheduler()).thenReturn(List.of(pedidoEmEntrega));
        when(mapeador.mapearPedidoCriadoDto(pedidoEmEntrega)).thenReturn(dto);

        handler.executar();

        verify(messagingTemplate).convertAndSend("/topico/pedidos/em-entrega", dto);
        verify(messagingTemplate, times(1)).convertAndSend(anyString(), any(PedidoCriadoDTO.class));
    }

    @Test
    @DisplayName("Deve enviar pedido CANCELADO para o tópico /topico/pedidos/cancelados-recente")
    void deveEnviarPedidoCanceladoParaTopicoCorreto() {
        PedidoCriadoDTO dto = Instancio.create(PedidoCriadoDTO.class);
        when(repositorio.buscarParaScheduler()).thenReturn(List.of(pedidoCancelado));
        when(mapeador.mapearPedidoCriadoDto(pedidoCancelado)).thenReturn(dto);

        handler.executar();

        verify(messagingTemplate).convertAndSend("/topico/pedidos/cancelados-recente", dto);
        verify(messagingTemplate, times(1)).convertAndSend(anyString(), any(PedidoCriadoDTO.class));
    }

    @Test
    @DisplayName("Deve enviar pedido ENTREGUE para o tópico /topico/pedidos/entregues-recente")
    void deveEnviarPedidoEntregueParaTopicoCorreto() {
        PedidoCriadoDTO dto = Instancio.create(PedidoCriadoDTO.class);
        when(repositorio.buscarParaScheduler()).thenReturn(List.of(pedidoEntregue));
        when(mapeador.mapearPedidoCriadoDto(pedidoEntregue)).thenReturn(dto);

        handler.executar();

        verify(messagingTemplate).convertAndSend("/topico/pedidos/entregues-recente", dto);
        verify(messagingTemplate, times(1)).convertAndSend(anyString(), any(PedidoCriadoDTO.class));
    }

    @Test
    @DisplayName("Não deve enviar mensagens nem acionar o mapeador quando não há pedidos")
    void naoDeveEnviarMensagensQuandoNaoHaPedidos() {
        when(repositorio.buscarParaScheduler()).thenReturn(List.of());

        handler.executar();

        verifyNoInteractions(messagingTemplate);
        verifyNoInteractions(mapeador);
    }

    @Test
    @DisplayName("Deve distribuir pedidos de status diferentes para seus tópicos corretos em uma única execução")
    void deveDistribuirPedidosDeStatusDiferentesParaTopicosCorretos() {
        PedidoCriadoDTO dtoPendente     = Instancio.create(PedidoCriadoDTO.class);
        PedidoCriadoDTO dtoEmPreparacao = Instancio.create(PedidoCriadoDTO.class);
        PedidoCriadoDTO dtoCancelado    = Instancio.create(PedidoCriadoDTO.class);
        PedidoCriadoDTO dtoEntregue     = Instancio.create(PedidoCriadoDTO.class);

        when(repositorio.buscarParaScheduler())
                .thenReturn(List.of(pedidoPendente, pedidoEmPreparacao, pedidoCancelado, pedidoEntregue));
        when(mapeador.mapearPedidoCriadoDto(pedidoPendente)).thenReturn(dtoPendente);
        when(mapeador.mapearPedidoCriadoDto(pedidoEmPreparacao)).thenReturn(dtoEmPreparacao);
        when(mapeador.mapearPedidoCriadoDto(pedidoCancelado)).thenReturn(dtoCancelado);
        when(mapeador.mapearPedidoCriadoDto(pedidoEntregue)).thenReturn(dtoEntregue);

        handler.executar();

        verify(messagingTemplate).convertAndSend("/topico/pedidos/pendentes",          dtoPendente);
        verify(messagingTemplate).convertAndSend("/topico/pedidos/em-preparacao",      dtoEmPreparacao);
        verify(messagingTemplate).convertAndSend("/topico/pedidos/cancelados-recente", dtoCancelado);
        verify(messagingTemplate).convertAndSend("/topico/pedidos/entregues-recente",  dtoEntregue);
        verify(messagingTemplate, times(4)).convertAndSend(anyString(), any(PedidoCriadoDTO.class));
    }

    @Test
    @DisplayName("Deve enviar pedidos do mesmo status do mais antigo para o mais recente por dataAtualizacao")
    void deveEnviarPedidosOrdenadosPorDataAtualizacaoCrescente() throws Exception {
        Pedido pedidoAntigo  = PedidoBuilder.umPedido().build();
        Pedido pedidoRecente = PedidoBuilder.umPedido().build();
        setarCampoHerdado(pedidoAntigo,  "dataAtualizacao", LocalDateTime.now().minusHours(2));
        setarCampoHerdado(pedidoRecente, "dataAtualizacao", LocalDateTime.now());

        PedidoCriadoDTO dtoAntigo  = Instancio.create(PedidoCriadoDTO.class);
        PedidoCriadoDTO dtoRecente = Instancio.create(PedidoCriadoDTO.class);
        when(repositorio.buscarParaScheduler()).thenReturn(List.of(pedidoRecente, pedidoAntigo));
        when(mapeador.mapearPedidoCriadoDto(pedidoAntigo)).thenReturn(dtoAntigo);
        when(mapeador.mapearPedidoCriadoDto(pedidoRecente)).thenReturn(dtoRecente);

        handler.executar();

        InOrder ordem = inOrder(messagingTemplate);
        ordem.verify(messagingTemplate).convertAndSend("/topico/pedidos/pendentes", dtoAntigo);
        ordem.verify(messagingTemplate).convertAndSend("/topico/pedidos/pendentes", dtoRecente);
    }

    private void setarCampoHerdado(Object obj, String nomeCampo, Object valor) {
        try {
            Class<?> clazz = obj.getClass();
            while (clazz != null) {
                try {
                    Field field = clazz.getDeclaredField(nomeCampo);
                    field.setAccessible(true);
                    field.set(obj, valor);
                    return;
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }
            throw new RuntimeException("Campo não encontrado: " + nomeCampo);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
