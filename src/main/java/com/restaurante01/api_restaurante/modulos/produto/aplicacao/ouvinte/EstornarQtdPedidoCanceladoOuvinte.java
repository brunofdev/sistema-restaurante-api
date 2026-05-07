package com.restaurante01.api_restaurante.modulos.produto.aplicacao.ouvinte;

import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.repositorio.OutboxRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCanceladoEvento;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso.EstornarQtdPedidoCanceladoCasoDeUso;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EstornarQtdPedidoCanceladoOuvinte {

    private final EstornarQtdPedidoCanceladoCasoDeUso estornarQtdPedidoCanceladoCasoDeUso;
    private final OutboxRepositorio outboxRepositorio;

    public EstornarQtdPedidoCanceladoOuvinte(EstornarQtdPedidoCanceladoCasoDeUso estornarQtdPedidoCanceladoCasoDeUso, OutboxRepositorio outboxRepositorio) {
        this.estornarQtdPedidoCanceladoCasoDeUso = estornarQtdPedidoCanceladoCasoDeUso;
        this.outboxRepositorio = outboxRepositorio;
    }

    @Async
    @TransactionalEventListener(phase=TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void quandoPedidoStatusCancelado(PedidoCanceladoEvento evento){
        OutboxEvento outboxEvento = outboxRepositorio.buscarPorAgregadoEIdAgregadoETipoEvento(Agregado.PEDIDO, evento.pedido().getId(), TipoEvento.ESTORNAR_ESTOQUE_PRODUTO);
        try{
            estornarQtdPedidoCanceladoCasoDeUso.executar(evento.itensPedidoPayload());
            outboxEvento.processar();
        }
        catch (Exception e){
            outboxEvento.registrarFalha();
        }
        finally {
            outboxRepositorio.salvar(outboxEvento);
        }


    }
}
