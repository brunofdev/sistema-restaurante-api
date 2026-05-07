package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.ouvinte.associacao;

import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.repositorio.OutboxRepositorio;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso.EstornarProdutoVendidoCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCanceladoEvento;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EstornarQtdVendidaOuvinte {
    private final EstornarProdutoVendidoCasoDeUso estornarProdutoVendidoCasoDeUso;
    private final OutboxRepositorio outboxRepositorio;

    public EstornarQtdVendidaOuvinte(EstornarProdutoVendidoCasoDeUso estornarProdutoVendidoCasoDeUso, OutboxRepositorio outboxRepositorio) {
        this.estornarProdutoVendidoCasoDeUso = estornarProdutoVendidoCasoDeUso;
        this.outboxRepositorio = outboxRepositorio;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void quandoPedidoForCancelado(PedidoCanceladoEvento evento){
        OutboxEvento outboxEvento = outboxRepositorio.buscarPorAgregadoEIdAgregadoETipoEvento(Agregado.PEDIDO, evento.pedido().getId(), TipoEvento.ESTORNAR_ESTOQUE_ASSOCIACAO);
        try{
            estornarProdutoVendidoCasoDeUso.executar(evento.itensPedidoPayload(), evento.pedido().getIdCardapio());
            outboxEvento.processar();
        }
        catch(Exception e){
            outboxEvento.registrarFalha();
        }
        finally {
            outboxRepositorio.salvar(outboxEvento);
        }
    }
}
