package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.StatusPedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mapeador.PedidoMapeador;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCanceladoEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoEntregueEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.excecao.PedidoNaoEncontradoExcecao;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizarStatusPedidoCasoDeUso {

    private final PedidoRepositorio pedidoRepository;
    private final PedidoMapeador pedidoMapeador;
    private final ApplicationEventPublisher eventPublisher;

    public AtualizarStatusPedidoCasoDeUso(PedidoRepositorio pedidoRepository, PedidoMapeador pedidoMapeador, ApplicationEventPublisher eventPublisher) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapeador = pedidoMapeador;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public PedidoDTO executar(Long id, StatusPedidoDTO novoStatusDto) {
        Pedido pedido = pedidoRepository.buscarPorId(id)
                .orElseThrow(() -> new PedidoNaoEncontradoExcecao("Pedido não localizado: " + id));
        pedido.mudarStatus(novoStatusDto.statusPedido());
        pedidoRepository.salvar(pedido);
        if (pedido.getStatusPedido() == StatusPedido.ENTREGUE) {
            eventPublisher.publishEvent(new PedidoEntregueEvento(pedido));
        }
        if(pedido.getStatusPedido() == StatusPedido.CANCELADO){
            eventPublisher.publishEvent(new PedidoCanceladoEvento(pedido));
        }
        return pedidoMapeador.mapearPedidoDto(pedido);
    }
}