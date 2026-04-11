package com.restaurante01.api_restaurante.modulos.cardapioproduto.ouvinte;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso.BaixarQtdCustomizadaCasoDeUso;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCriadoEvento;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BaixarQuantidadeEstoqueOuvinte {

    private final BaixarQtdCustomizadaCasoDeUso baixarQtdCustomizadaCasoDeUso;

    public BaixarQuantidadeEstoqueOuvinte(BaixarQtdCustomizadaCasoDeUso baixarQtdCustomizadaCasoDeUso) {
        this.baixarQtdCustomizadaCasoDeUso = baixarQtdCustomizadaCasoDeUso;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void quandoPedidoForCriado(PedidoCriadoEvento evento){
        baixarQtdCustomizadaCasoDeUso.executar(evento.idCardapio(), evento.pedido().getItens());
    }
}
