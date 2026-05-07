package com.restaurante01.api_restaurante.modulos.cupom.aplicacao.ouvinte;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.compartilhado.aplicacao.OutboxEventoHandler;
import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.modulos.cupom.aplicacao.casodeuso.ConsumirCupomCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.CodigoCupom;
import org.springframework.stereotype.Component;

@Component
public class ConsumirCupomHandler implements OutboxEventoHandler {

    private final ConsumirCupomCasoDeUso consumirCupomCasoDeUso;
    private final ObjectMapper objectMapper;

    public ConsumirCupomHandler(ConsumirCupomCasoDeUso consumirCupomCasoDeUso, ObjectMapper objectMapper) {
        this.consumirCupomCasoDeUso = consumirCupomCasoDeUso;
        this.objectMapper = objectMapper;
    }

    @Override
    public TipoEvento tipoEvento() {
        return TipoEvento.CONSUMIR_CUPOM;
    }

    @Override
    public void processar(OutboxEvento  outboxEvento) {
        try {
            CodigoCupom codigoCupom = new CodigoCupom(objectMapper.readValue(outboxEvento.getPayload(), String.class));
            consumirCupomCasoDeUso.executar(codigoCupom);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao reprocessar outbox id=" +
                    outboxEvento.getId(), e);
        }
    }
}
