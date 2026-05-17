package com.restaurante01.api_restaurante.modulos.fidelidade.aplicacao.ouvinte;

import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.repositorio.OutboxRepositorio;
import com.restaurante01.api_restaurante.modulos.fidelidade.aplicacao.casodeuso.ProcessarPontuacaoAposPedidoEntregueCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoEntregueEvento;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
public class PedidoEntregueOuvinte {

    private final ProcessarPontuacaoAposPedidoEntregueCasoDeUso casoDeUso;
    private final OutboxRepositorio outboxRepositorio;

    @Async
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void quandoPedidoEntregue(PedidoEntregueEvento evento) {
        OutboxEvento outbox = outboxRepositorio.buscarPorAgregadoEIdAgregadoETipoEvento(
                Agregado.PEDIDO, evento.pedido().getId(), TipoEvento.COMPUTAR_PONTUACAO_FIDELIDADE);
        try {
            casoDeUso.executar(
                    evento.pedido().getCliente().clienteId(),
                    evento.pedido().getValorTotal());
            outbox.processar();
        } catch (Exception e) {
            outbox.registrarFalha();
        } finally {
            outboxRepositorio.salvar(outbox);
        }
    }
}
