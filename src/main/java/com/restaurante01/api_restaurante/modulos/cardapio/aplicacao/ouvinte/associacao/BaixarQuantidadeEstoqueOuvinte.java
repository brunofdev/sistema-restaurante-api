package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.ouvinte.associacao;

import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso.BaixarQtdCustomizadaCasoDeUso;

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
        baixarQtdCustomizadaCasoDeUso.executar(evento.pedido().getIdCardapio(), evento.pedido().getItens());
    }
}
