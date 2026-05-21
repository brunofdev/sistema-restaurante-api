package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.GatilhoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.StatusPedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoCriadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mapeador.PedidoMapeador;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.schuduler.OrganizaPedidosPorStatusHandler;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCanceladoEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoEntregueEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao.PedidoNaoEncontradoExcecao;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoOutboxPorta;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.*;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AtualizarStatusPedidoCasoDeUso {

    private final PedidoRepositorio pedidoRepository;
    private final PedidoMapeador pedidoMapeador;
    private final ApplicationEventPublisher publicarEvento;
    private final PedidoOutboxPorta pedidoOutboxPorta;
    private final ObjectMapper  objectMapper;
    private final OrganizaPedidosPorStatusHandler organizaPedidosPorStatusHandler;

    @Transactional
    public PedidoCriadoDTO executar(Long id, StatusPedidoDTO novoStatusDto) throws JsonProcessingException {
        Pedido pedido = pedidoRepository.buscarPorId(id)
                .orElseThrow(() -> new PedidoNaoEncontradoExcecao("Pedido não localizado: " + id));
        pedido.mudarStatus(novoStatusDto.statusPedido());
        Pedido pedidoAtualizado = pedidoRepository.salvar(pedido);
        organizaPedidosPorStatusHandler.executar();
        switch (pedido.getStatusPedido()) {
            case ENTREGUE -> publicaEventosSeEntregue(pedidoAtualizado);
            case CANCELADO -> publicaEventosSeCancelado(pedidoAtualizado);
        }
        return pedidoMapeador.mapearPedidoCriadoDto(pedidoAtualizado);
    }
    private void publicaEventosSeEntregue(Pedido pedido) throws JsonProcessingException {
        List<ItemPedidoAvaliacaoPayload> itensParaAvaliacao = pedidoMapeador.mapearItemPedidoAvaliacaoPayload(pedido.getItens());
        PedidoEntregueAvaliacaoPayload pedidoEntregueAvaliacaoPayload = new PedidoEntregueAvaliacaoPayload(pedido.getId(), pedido.getCliente().clienteId(), itensParaAvaliacao);
        PedidoEntregueFidelidadePayload pedidoEntregueFidelidadePayload = new PedidoEntregueFidelidadePayload(pedido.getId(),pedido.getCliente().clienteId(),pedido.getValorBruto(),LocalDateTime.now());
        pedidoOutboxPorta.guardarEvento(Agregado.PEDIDO, pedido.getId(), GatilhoEvento.PEDIDO_ENTREGUE, TipoEvento.COMPUTAR_PONTUACAO_PEDIDO_ENTREGUE, objectMapper.writeValueAsString(pedidoEntregueFidelidadePayload));
        pedidoOutboxPorta.guardarEvento(Agregado.PEDIDO, pedido.getId(), GatilhoEvento.PEDIDO_ENTREGUE, TipoEvento.CRIAR_AVALIACAO, objectMapper.writeValueAsString(pedidoEntregueAvaliacaoPayload));
        publicarEvento.publishEvent(new PedidoEntregueEvento(pedido, itensParaAvaliacao));
    }
    private void publicaEventosSeCancelado(Pedido pedido) throws JsonProcessingException {
        List<ItemPedidoClientePayload> itensParaEstornar = pedidoMapeador.mapearItemPedidoClientePayload(pedido.getItens());
        PedidoCriadoPayload pedidoCriadoPayload = new PedidoCriadoPayload(pedido.getId(), pedido.getIdCardapio(), itensParaEstornar);
        publicarEvento.publishEvent(new PedidoCanceladoEvento(pedido, itensParaEstornar));
        pedidoOutboxPorta.guardarEvento(Agregado.PEDIDO, pedido.getId(), GatilhoEvento.PEDIDO_CANCELADO, TipoEvento.ESTORNAR_ESTOQUE_ASSOCIACAO, objectMapper.writeValueAsString(pedidoCriadoPayload));
        pedidoOutboxPorta.guardarEvento(Agregado.PEDIDO, pedido.getId(), GatilhoEvento.PEDIDO_CANCELADO, TipoEvento.ESTORNAR_ESTOQUE_PRODUTO, objectMapper.writeValueAsString(pedidoCriadoPayload));
    }
}