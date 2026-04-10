package com.restaurante01.api_restaurante.modulos.cardapioproduto.ouvinte;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso.BaixarQuantidadeEstoqueCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCriadoEvento;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BaixarQuantidadeEstoqueOuvinte {

    private final BaixarQuantidadeEstoqueCasoDeUso baixarQuantidadeEstoqueCasoDeUso;

    public BaixarQuantidadeEstoqueOuvinte(BaixarQuantidadeEstoqueCasoDeUso baixarQuantidadeEstoqueCasoDeUso) {
        this.baixarQuantidadeEstoqueCasoDeUso = baixarQuantidadeEstoqueCasoDeUso;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void quandoPedidoForCriado(PedidoCriadoEvento evento){
        baixarQuantidadeEstoqueCasoDeUso.executar(evento.estoqueValidado(), evento.pedido(), evento.idCardapio());
    }
}
