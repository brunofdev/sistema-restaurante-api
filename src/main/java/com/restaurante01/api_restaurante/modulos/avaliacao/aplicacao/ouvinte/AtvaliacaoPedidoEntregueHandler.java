package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.ouvinte;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.compartilhado.aplicacao.OutboxEventoHandler;
import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso.CriarAvaliacaoCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.PedidoEntreguePayload;
import org.springframework.stereotype.Component;

@Component
public class AtvaliacaoPedidoEntregueHandler implements OutboxEventoHandler {

    private final CriarAvaliacaoCasoDeUso criarAvaliacaoCasoDeUso;
    private final ObjectMapper mapper;

    public AtvaliacaoPedidoEntregueHandler(CriarAvaliacaoCasoDeUso criarAvaliacaoCasoDeUso, ObjectMapper mapper) {
        this.criarAvaliacaoCasoDeUso = criarAvaliacaoCasoDeUso;
        this.mapper = mapper;
    }

    @Override
    public TipoEvento tipoEvento() {
        return TipoEvento.CRIAR_AVALIACAO;
    }

    @Override
    public void processar(OutboxEvento outboxEvento) {
        try {
            PedidoEntreguePayload payload = mapper.readValue(
                    outboxEvento.getPayload(),
                    PedidoEntreguePayload.class
            );
            criarAvaliacaoCasoDeUso.executar(payload.pedidoId(),
                    payload.clienteId());
        }
        catch(Exception e){
            throw new RuntimeException("Erro ao reprocessar outbox id=" +
                    outboxEvento.getId(), e);
        }
    }
}
