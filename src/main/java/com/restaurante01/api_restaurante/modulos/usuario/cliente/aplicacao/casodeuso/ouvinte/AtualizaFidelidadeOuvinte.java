package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso.ouvinte;


import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso.AtualizarFidelidadeClienteCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoEntregueEvento;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class AtualizaFidelidadeOuvinte {

    private final AtualizarFidelidadeClienteCasoDeUso atualizarFidelidadeClienteCasoDeUso;

    public AtualizaFidelidadeOuvinte(AtualizarFidelidadeClienteCasoDeUso atualizarFidelidadeClienteCasoDeUso) {
        this.atualizarFidelidadeClienteCasoDeUso = atualizarFidelidadeClienteCasoDeUso;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void quandoPedidoStatusEntregue (PedidoEntregueEvento evento){
        atualizarFidelidadeClienteCasoDeUso.executar(evento.pedido().getCliente().clienteId(), evento.pedido().getValorTotal());
    }
}
