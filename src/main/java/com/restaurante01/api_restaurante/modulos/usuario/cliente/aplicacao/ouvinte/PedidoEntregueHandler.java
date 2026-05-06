package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.ouvinte;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.compartilhado.aplicacao.OutboxEventoHandler;
import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.PedidoEntreguePayload;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso.AtualizarFidelidadeClienteCasoDeUso;
import org.springframework.stereotype.Component;

@Component
public class PedidoEntregueHandler implements OutboxEventoHandler {

    private final AtualizarFidelidadeClienteCasoDeUso atualizarFidelidade;
    private final ObjectMapper mapper;

    public PedidoEntregueHandler(AtualizarFidelidadeClienteCasoDeUso atualizarFidelidade, ObjectMapper mapper) {
        this.atualizarFidelidade = atualizarFidelidade;
        this.mapper = mapper;
    }

    @Override
    public TipoEvento tipoEvento() {
        return TipoEvento.COMPUTAR_PONTUACAO_FIDELIDADE;
    }

    @Override
    public void processar(OutboxEvento outboxEvento) {
        try {
            PedidoEntreguePayload payload = mapper.readValue(
                    outboxEvento.getPayload(),
                    PedidoEntreguePayload.class
            );
            atualizarFidelidade.executar(payload.clienteId(),
                    payload.valorTotal());
        }
        catch(Exception e){
        throw new RuntimeException("Erro ao reprocessar outbox id=" +
                outboxEvento.getId(), e);
        }
    }
}

