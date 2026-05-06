package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.StatusPedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoCriadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mapeador.PedidoMapeador;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCanceladoEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoEntregueEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao.PedidoNaoEncontradoExcecao;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoOutboxPorta;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ItemPedidoPayload;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.PedidoCriadoPayload;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.PedidoEntreguePayload;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AtualizarStatusPedidoCasoDeUso {

    private final PedidoRepositorio pedidoRepository;
    private final PedidoMapeador pedidoMapeador;
    private final ApplicationEventPublisher publicarEvento;
    private final PedidoOutboxPorta pedidoOutboxPorta;
    private final ObjectMapper  objectMapper;

    public AtualizarStatusPedidoCasoDeUso(PedidoRepositorio pedidoRepository, PedidoMapeador pedidoMapeador, ApplicationEventPublisher publicarEvento, PedidoOutboxPorta pedidoOutboxPorta, ObjectMapper objectMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapeador = pedidoMapeador;
        this.publicarEvento = publicarEvento;
        this.pedidoOutboxPorta = pedidoOutboxPorta;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public PedidoCriadoDTO executar(Long id, StatusPedidoDTO novoStatusDto) throws JsonProcessingException {
        Pedido pedido = pedidoRepository.buscarPorId(id)
                .orElseThrow(() -> new PedidoNaoEncontradoExcecao("Pedido não localizado: " + id));
        pedido.mudarStatus(novoStatusDto.statusPedido());
        pedidoRepository.salvar(pedido);
        if (pedido.getStatusPedido() == StatusPedido.ENTREGUE) {
            PedidoEntreguePayload pedidoEntreguePayload = new PedidoEntreguePayload(pedido.getId(),pedido.getCliente().clienteId(),pedido.getValorBruto(),LocalDateTime.now());
            pedidoOutboxPorta.guardarEvento(Agregado.PEDIDO,pedido.getId(),TipoEvento.COMPUTAR_PONTUACAO_FIDELIDADE,objectMapper.writeValueAsString(pedidoEntreguePayload));
            publicarEvento.publishEvent(new PedidoEntregueEvento(pedido));
        }
        if(pedido.getStatusPedido() == StatusPedido.CANCELADO){
            List<ItemPedidoPayload> itensParaEstornar = pedidoMapeador.mapearItemPedidoPayload(pedido.getItens());
            PedidoCriadoPayload pedidoCriadoPayload = new PedidoCriadoPayload(pedido.getId(), pedido.getIdCardapio(), itensParaEstornar);
            publicarEvento.publishEvent(new PedidoCanceladoEvento(pedido, itensParaEstornar));
            pedidoOutboxPorta.guardarEvento(Agregado.PEDIDO, pedido.getId(), TipoEvento.ESTORNAR_ESTOQUE_ASSOCIACAO, objectMapper.writeValueAsString(pedidoCriadoPayload));
            pedidoOutboxPorta.guardarEvento(Agregado.PEDIDO, pedido.getId(), TipoEvento.ESTORNAR_ESTOQUE_PRODUTO, objectMapper.writeValueAsString(pedidoCriadoPayload));
        }
        return pedidoMapeador.mapearPedidoCriadoDto(pedido);
    }
}