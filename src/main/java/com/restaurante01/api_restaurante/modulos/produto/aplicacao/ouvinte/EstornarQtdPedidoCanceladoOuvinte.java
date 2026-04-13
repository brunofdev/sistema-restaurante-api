package com.restaurante01.api_restaurante.modulos.produto.aplicacao.ouvinte;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCanceladoEvento;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso.EstornarQtdPedidoCanceladoCasoDeUso;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EstornarQtdPedidoCanceladoOuvinte {

    private final EstornarQtdPedidoCanceladoCasoDeUso estornarQtdPedidoCanceladoCasoDeUso;

    public EstornarQtdPedidoCanceladoOuvinte(EstornarQtdPedidoCanceladoCasoDeUso estornarQtdPedidoCanceladoCasoDeUso) {
        this.estornarQtdPedidoCanceladoCasoDeUso = estornarQtdPedidoCanceladoCasoDeUso;
    }

    @Async
    @TransactionalEventListener(phase=TransactionPhase.AFTER_COMMIT)
    public void quandoPedidoStatusCancelado(PedidoCanceladoEvento evento){
        estornarQtdPedidoCanceladoCasoDeUso.executar(evento.pedido().getItens());
    }
}
