package com.restaurante01.api_restaurante.modulos.produto.aplicacao.ouvinte;

import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.repositorio.OutboxRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCriadoEvento;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso.BaixarQuantidadeProdutoCasoDeUso;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BaixarQtdPosPedidoOuvinte {

  private final BaixarQuantidadeProdutoCasoDeUso baixarQuantidadeProdutoCasoDeUso;
  private final OutboxRepositorio outboxRepositorio;

    public BaixarQtdPosPedidoOuvinte(BaixarQuantidadeProdutoCasoDeUso baixarQuantidadeProdutoCasoDeUso, OutboxRepositorio outboxRepositorio) {
        this.baixarQuantidadeProdutoCasoDeUso = baixarQuantidadeProdutoCasoDeUso;
        this.outboxRepositorio = outboxRepositorio;
    }


    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void quandoPedidoForCriado(PedidoCriadoEvento evento){
        OutboxEvento outboxEvento = outboxRepositorio.buscarPorAgregadoEIdAgregadoETipoEvento(Agregado.PEDIDO, evento.pedido().getId(), TipoEvento.BAIXAR_ESTOQUE_PRODUTO);
        try{
            baixarQuantidadeProdutoCasoDeUso.executar(evento.ItensPedidoPayload());
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
