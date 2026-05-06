package com.restaurante01.api_restaurante.modulos.cupom.aplicacao.ouvinte;

import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.Agregado;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.repositorio.OutboxRepositorio;
import com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso.ConsumirCupomCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CodigoCupom;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCriadoEvento;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PedidoCriadoOuvinte {


    private final ConsumirCupomCasoDeUso consumirCupomCasoDeUso;
    private final OutboxRepositorio outboxRepositorio;


    public PedidoCriadoOuvinte(ConsumirCupomCasoDeUso consumirCupomCasoDeUso, OutboxRepositorio outboxRepositorio) {
        this.consumirCupomCasoDeUso = consumirCupomCasoDeUso;
        this.outboxRepositorio = outboxRepositorio;
    }

    @Async
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void quandoPedidoComCupomForRealizado(PedidoCriadoEvento evento) {
        if (!(evento.pedido().getCupom() == null)){
            OutboxEvento outboxEvento = outboxRepositorio.buscarPorAgregadoEIdAgregadoETipoEvento(Agregado.PEDIDO, evento.pedido().getId(), TipoEvento.CONSUMIR_CUPOM);
            try{
                consumirCupomCasoDeUso.executar(new CodigoCupom(evento.pedido().getCupom().codigoCupom()));
                outboxEvento.processar();
            }
            catch (Exception e){
                outboxEvento.registrarFalha();
            }
            finally {
                outboxRepositorio.salvar(outboxEvento);
            }
        }
    }
}
