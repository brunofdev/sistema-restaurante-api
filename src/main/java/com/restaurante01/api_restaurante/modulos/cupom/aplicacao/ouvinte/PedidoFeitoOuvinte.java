package com.restaurante01.api_restaurante.modulos.cupom.aplicacao.ouvinte;

import com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso.ConsumirCupomCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CodigoCupom;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.evento.PedidoCriadoEvento;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PedidoFeitoOuvinte {


    private final ConsumirCupomCasoDeUso consumirCupomCasoDeUso;

    public PedidoFeitoOuvinte(ConsumirCupomCasoDeUso consumirCupomCasoDeUso) {
        this.consumirCupomCasoDeUso = consumirCupomCasoDeUso;
    }

    @TransactionalEventListener
    public void quandoPedidoComCupomForRealizado(PedidoCriadoEvento evento) {
        if (!(evento.pedido().getCupom() == null)) {
            consumirCupomCasoDeUso.executar(new CodigoCupom(evento.pedido().getCupom().codigoCupom()));
        }
    }
}
