package com.restaurante01.api_restaurante.modulos.produto.aplicacao.ouvinte;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.compartilhado.aplicacao.OutboxEventoHandler;
import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.PedidoCriadoPayload;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso.EstornarQtdPedidoCanceladoCasoDeUso;
import org.springframework.stereotype.Component;

@Component
public class EstornarQtdPedidoCanceladoHandler implements OutboxEventoHandler {

    private final EstornarQtdPedidoCanceladoCasoDeUso estornarQtdPedidoCanceladoCasoDeUso;
    private final ObjectMapper objectMapper;

    public EstornarQtdPedidoCanceladoHandler(EstornarQtdPedidoCanceladoCasoDeUso estornarQtdPedidoCanceladoCasoDeUso, ObjectMapper objectMapper) {
        this.estornarQtdPedidoCanceladoCasoDeUso = estornarQtdPedidoCanceladoCasoDeUso;
        this.objectMapper = objectMapper;
    }

    @Override
    public TipoEvento tipoEvento() {
        return TipoEvento.ESTORNAR_ESTOQUE_PRODUTO;
    }

    @Override
    public void processar(OutboxEvento outboxEvento){
        try{
            PedidoCriadoPayload payload = objectMapper.readValue(outboxEvento.getPayload(), PedidoCriadoPayload.class);
            estornarQtdPedidoCanceladoCasoDeUso.executar(payload.itens());
        }
        catch(Exception e){
            throw new RuntimeException("Erro ao processar Outbox id= " + outboxEvento.getId() + "| Exception:  " +  e.getMessage());
        }
    }
}
