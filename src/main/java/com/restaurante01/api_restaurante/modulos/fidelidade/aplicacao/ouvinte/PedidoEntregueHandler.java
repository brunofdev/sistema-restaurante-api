package com.restaurante01.api_restaurante.modulos.fidelidade.aplicacao.ouvinte;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.compartilhado.aplicacao.OutboxEventoHandler;
import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.modulos.fidelidade.aplicacao.casodeuso.ProcessaPontuacaoPedidoEntregueCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.PedidoEntregueFidelidadePayload;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PedidoEntregueHandler implements OutboxEventoHandler {

    private final ProcessaPontuacaoPedidoEntregueCasoDeUso casoDeUso;
    private final ObjectMapper mapper;

    @Override
    public TipoEvento tipoEvento() {
        return TipoEvento.COMPUTAR_PONTUACAO_PEDIDO_ENTREGUE;
    }

    @Override
    public void processar(OutboxEvento outboxEvento) {
        try {
            PedidoEntregueFidelidadePayload payload = mapper.readValue(
                    outboxEvento.getPayload(),
                    PedidoEntregueFidelidadePayload.class
            );
            casoDeUso.executar(payload.clienteId(), payload.valorTotal());
        }
        catch(Exception e){
            throw new RuntimeException("Erro ao reprocessar outbox id=" +
                    outboxEvento.getId(), e);
        }
    }
}
