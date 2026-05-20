package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.schuduler;

import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoCriadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mapeador.PedidoMapeador;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Service
@AllArgsConstructor
public class OrganizaPedidosPorStatusHandler {

    private final PedidoRepositorio repositorio;
    private final SimpMessagingTemplate messagingTemplate;
    private final PedidoMapeador mapeador;


    @Scheduled(fixedDelay = 60_000)
    @Transactional(readOnly = true)
    public void executar(){
        PriorityQueue<Pedido> pedidosPendentes    =   new PriorityQueue<>(Comparator.comparing(Pedido::getDataAtualizacao));
        PriorityQueue<Pedido> pedidosEmPreparacao =   new PriorityQueue<>(Comparator.comparing(Pedido::getDataAtualizacao));
        PriorityQueue<Pedido> pedidosEmEntrega    =   new PriorityQueue<>(Comparator.comparing(Pedido::getDataAtualizacao));
        PriorityQueue<Pedido> pedidosCancelados   =   new PriorityQueue<>(Comparator.comparing(Pedido::getDataAtualizacao));
        PriorityQueue<Pedido> pedidosEntregues    =   new PriorityQueue<>(Comparator.comparing(Pedido::getDataAtualizacao));
        List<Pedido> pedidos = repositorio.buscarParaScheduler();
        for (Pedido pedido : pedidos) {
            switch (pedido.getStatusPedido()){
                case PENDENTE          ->  pedidosPendentes.offer(pedido);
                case EM_PREPARACAO     ->  pedidosEmPreparacao.offer(pedido);
                case SAIU_PARA_ENTREGA ->  pedidosEmEntrega.offer(pedido);
                case CANCELADO         ->  pedidosCancelados.offer(pedido);
                case ENTREGUE          ->  pedidosEntregues.offer(pedido);
                }
            }
        enviaPedidosPendentes(pedidosPendentes);
        enviaPedidosEmPreparacao(pedidosEmPreparacao);
        enviaPedidosEmEntrega(pedidosEmEntrega);
        enviaPedidosCancelados(pedidosCancelados);
        enviaPedidosEntregues(pedidosEntregues);
    }

    public void enviaPedidosPendentes(PriorityQueue<Pedido> pedidosPendentes) {
        while (!pedidosPendentes.isEmpty()){
            Pedido pedido = pedidosPendentes.poll();
            PedidoCriadoDTO dto = mapeador.mapearPedidoCriadoDto(pedido);
            messagingTemplate.convertAndSend("/topico/pedidos/pendentes", dto);
        }
    }
    public void enviaPedidosEmPreparacao(PriorityQueue<Pedido> pedidosEmPreparacao){
        while (!pedidosEmPreparacao.isEmpty()){
            Pedido pedido = pedidosEmPreparacao.poll();
            PedidoCriadoDTO dto = mapeador.mapearPedidoCriadoDto(pedido);
            messagingTemplate.convertAndSend("/topico/pedidos/em-preparacao", dto);
        }
    }
    public void enviaPedidosEmEntrega(PriorityQueue<Pedido> pedidosEmEntrega){
        while (!pedidosEmEntrega.isEmpty()){
            Pedido pedido = pedidosEmEntrega.poll();
            PedidoCriadoDTO dto = mapeador.mapearPedidoCriadoDto(pedido);
            messagingTemplate.convertAndSend("/topico/pedidos/em-entrega", dto);

        }
    }
    public void enviaPedidosCancelados(PriorityQueue<Pedido> pedidosCancelados){
        while (!pedidosCancelados.isEmpty()){
            Pedido pedido = pedidosCancelados.poll();
            PedidoCriadoDTO dto = mapeador.mapearPedidoCriadoDto(pedido);
            messagingTemplate.convertAndSend("/topico/pedidos/cancelados-recente", dto);
        }
    }
    public void enviaPedidosEntregues(PriorityQueue<Pedido> pedidosEntregue){
        while (!pedidosEntregue.isEmpty()){
            Pedido pedido = pedidosEntregue.poll();
            PedidoCriadoDTO dto = mapeador.mapearPedidoCriadoDto(pedido);
            messagingTemplate.convertAndSend("/topico/pedidos/entregues-recente", dto);
        }
    }

}
