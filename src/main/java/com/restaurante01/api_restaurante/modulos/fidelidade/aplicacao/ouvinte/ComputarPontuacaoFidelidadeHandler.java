package com.restaurante01.api_restaurante.modulos.fidelidade.aplicacao.ouvinte;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.compartilhado.aplicacao.OutboxEventoHandler;
import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.modulos.fidelidade.aplicacao.casodeuso.ProcessarPontuacaoAposPedidoEntregueCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.PedidoEntregueClientePayload;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ComputarPontuacaoFidelidadeHandler implements OutboxEventoHandler {

    private final ProcessarPontuacaoAposPedidoEntregueCasoDeUso casoDeUso;
    private final ObjectMapper mapper;

    @Override
    public TipoEvento tipoEvento() {
        return TipoEvento.COMPUTAR_PONTUACAO_FIDELIDADE;
    }

    @Override
    public void processar(OutboxEvento outboxEvento) {
        try {
            PedidoEntregueClientePayload payload = mapper.readValue(
                    outboxEvento.getPayload(), PedidoEntregueClientePayload.class);
            casoDeUso.executar(payload.clienteId(), payload.valorTotal());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao reprocessar outbox id=" + outboxEvento.getId(), e);
        }
    }
}
