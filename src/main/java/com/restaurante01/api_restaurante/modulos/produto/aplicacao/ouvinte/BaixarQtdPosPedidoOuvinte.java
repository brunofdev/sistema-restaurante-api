package com.restaurante01.api_restaurante.modulos.produto.aplicacao.ouvinte;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCriadoEvento;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso.BaixarQuantidadeProdutoCasoDeUso;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BaixarQtdPosPedidoOuvinte {

  private final BaixarQuantidadeProdutoCasoDeUso baixarQuantidadeProdutoCasoDeUso;

    public BaixarQtdPosPedidoOuvinte(BaixarQuantidadeProdutoCasoDeUso baixarQuantidadeProdutoCasoDeUso) {
        this.baixarQuantidadeProdutoCasoDeUso = baixarQuantidadeProdutoCasoDeUso;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void quandoPedidoForCriado(PedidoCriadoEvento evento){
        baixarQuantidadeProdutoCasoDeUso.executar(evento.pedido().getItens());
    }
}
