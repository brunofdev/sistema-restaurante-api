package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.ouvinte;

import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.repositorio.OutboxRepositorio;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso.CriarAvaliacaoCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoEntregueEvento;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class AvaliacaoPedidoEntregueOuvinte {

    private final CriarAvaliacaoCasoDeUso criarAvaliacaoCasoDeUso;
    private final OutboxRepositorio outboxRepositorio;

    public AvaliacaoPedidoEntregueOuvinte(CriarAvaliacaoCasoDeUso criarAvaliacaoCasoDeUso, OutboxRepositorio outboxRepositorio) {
        this.criarAvaliacaoCasoDeUso = criarAvaliacaoCasoDeUso;
        this.outboxRepositorio = outboxRepositorio;
    }

    @Async
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void criaAvaliacaoAposPedidoEntregue(PedidoEntregueEvento evento){
        OutboxEvento outboxEvento = outboxRepositorio.buscarPorAgregadoEIdAgregadoETipoEvento(Agregado.PEDIDO, evento.pedido().getId(), TipoEvento.CRIAR_AVALIACAO);
        try {
            criarAvaliacaoCasoDeUso.executar(evento.pedido().getId(), evento.pedido().getCliente().clienteId());
            outboxEvento.processar();
        }
        catch (Exception e){
            outboxEvento.registrarFalha();
        }
        finally{
            outboxRepositorio.salvar(outboxEvento);
        }
    }
}
