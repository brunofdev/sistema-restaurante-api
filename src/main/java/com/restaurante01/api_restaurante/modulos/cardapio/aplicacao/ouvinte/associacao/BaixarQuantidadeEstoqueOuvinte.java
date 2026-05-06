package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.ouvinte.associacao;

import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.repositorio.OutboxRepositorio;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso.BaixarQtdCustomizadaCasoDeUso;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCriadoEvento;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BaixarQuantidadeEstoqueOuvinte {

    private final BaixarQtdCustomizadaCasoDeUso baixarQtdCustomizadaCasoDeUso;
    private final OutboxRepositorio outboxRepositorio;

    public BaixarQuantidadeEstoqueOuvinte(BaixarQtdCustomizadaCasoDeUso baixarQtdCustomizadaCasoDeUso, OutboxRepositorio outboxRepositorio) {
        this.baixarQtdCustomizadaCasoDeUso = baixarQtdCustomizadaCasoDeUso;
        this.outboxRepositorio = outboxRepositorio;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void quandoPedidoForCriado(PedidoCriadoEvento evento){
        OutboxEvento outbox = outboxRepositorio
                .buscarPorAgregadoEIdAgregadoETipoEvento(Agregado.PEDIDO,
                        evento.pedido().getId(), TipoEvento.BAIXAR_ESTOQUE_ASSOCIACAO);
        try {
            baixarQtdCustomizadaCasoDeUso.executar(
                    evento.pedido().getIdCardapio(),
                    evento.ItensPedidoPayload()
            );
            outbox.processar();
        } catch (Exception e) {
            outbox.registrarFalha();
        } finally {
            outboxRepositorio.salvar(outbox);
        }
    }
}
