package com.restaurante01.api_restaurante.modulos.fidelidade.aplicacao.ouvinte;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.repositorio.OutboxRepositorio;
import com.restaurante01.api_restaurante.modulos.fidelidade.aplicacao.casodeuso.ProcessaPontuacaoPedidoEntregueCasoDeUso;
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

    private final OutboxRepositorio outboxRepositorio;
    private final ProcessaPontuacaoPedidoEntregueCasoDeUso casoDeUso;

    @Async
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void atualizaPontuacaoAposPedidoSerEntregue(PedidoEntregueEvento evento) {
        OutboxEvento outboxEvento = outboxRepositorio.
                buscarPorAgregadoEIdAgregadoETipoEvento(Agregado.PEDIDO,
                        evento.pedido().getId(),
                        TipoEvento.COMPUTAR_PONTUACAO_PEDIDO_ENTREGUE);
        try{
            casoDeUso.executar(evento.pedido().getCliente().clienteId(), evento.pedido().getValorTotal());
            outboxEvento.processar();
        } catch (Exception e) {
            outboxEvento.registrarFalha();
        }
        finally {
            outboxRepositorio.salvar(outboxEvento);
        }
    }
}
