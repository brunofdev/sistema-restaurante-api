package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.ouvinte;

import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.repositorio.OutboxRepositorio;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso.AtualizarFidelidadeClienteCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoEntregueEvento;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class AtualizaFidelidadeOuvinte {

    private final AtualizarFidelidadeClienteCasoDeUso atualizarFidelidade;
    private final OutboxRepositorio outboxRepositorio;


    public AtualizaFidelidadeOuvinte(AtualizarFidelidadeClienteCasoDeUso atualizarFidelidade, OutboxRepositorio outboxRepositorio) {
        this.atualizarFidelidade = atualizarFidelidade;
        this.outboxRepositorio = outboxRepositorio;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void quandoPedidoStatusEntregue(PedidoEntregueEvento evento) {
        OutboxEvento outbox = outboxRepositorio
                .buscarPorAgregadoEIdAgregadoETipoEvento(Agregado.PEDIDO,
                        evento.pedido().getId(), TipoEvento.PEDIDO_ENTREGUE);
        try {
            atualizarFidelidade.executar(
                    evento.pedido().getCliente().clienteId(),
                    evento.pedido().getValorTotal()
            );
            outbox.processar();
        } catch (Exception e) {
            outbox.registrarFalha();
        } finally {
            outboxRepositorio.salvar(outbox);
        }
    }
}