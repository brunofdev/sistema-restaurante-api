package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.ouvinte;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.compartilhado.aplicacao.OutboxEventoHandler;
import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.PedidoEntregueClientePayload;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso.AtualizarFidelidadeClienteCasoDeUso;
import org.springframework.stereotype.Component;

@Component
public class AtualizaFidelidadeHandler implements OutboxEventoHandler {

    private final AtualizarFidelidadeClienteCasoDeUso atualizarFidelidade;
    private final ObjectMapper mapper;

    public AtualizaFidelidadeHandler(AtualizarFidelidadeClienteCasoDeUso atualizarFidelidade, ObjectMapper mapper) {
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
            PedidoEntregueClientePayload payload = mapper.readValue(
                    outboxEvento.getPayload(),
                    PedidoEntregueClientePayload.class
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

