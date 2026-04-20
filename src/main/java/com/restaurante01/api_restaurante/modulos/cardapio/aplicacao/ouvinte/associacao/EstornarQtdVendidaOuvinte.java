package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.ouvinte.associacao;

import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso.EstornarProdutoVendidoCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCanceladoEvento;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EstornarQtdVendidaOuvinte {
    private final EstornarProdutoVendidoCasoDeUso estornarProdutoVendidoCasoDeUso;

    public EstornarQtdVendidaOuvinte(EstornarProdutoVendidoCasoDeUso estornarProdutoVendidoCasoDeUso) {
        this.estornarProdutoVendidoCasoDeUso = estornarProdutoVendidoCasoDeUso;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void quandoPedidoForCancelado(PedidoCanceladoEvento evento){
        estornarProdutoVendidoCasoDeUso.executar(evento.pedido().getItens(), evento.pedido().getIdCardapio());
    }
}
