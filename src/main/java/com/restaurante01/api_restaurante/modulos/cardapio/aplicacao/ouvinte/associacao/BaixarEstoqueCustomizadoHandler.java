package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.ouvinte.associacao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.compartilhado.aplicacao.OutboxEventoHandler;
import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso.BaixarQtdCustomizadaCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.PedidoCriadoPayload;
import org.springframework.stereotype.Component;

@Component
public class BaixarEstoqueCustomizadoHandler implements OutboxEventoHandler {

    private final BaixarQtdCustomizadaCasoDeUso baixarQtdCustomizadaCasoDeUso;
    private final ObjectMapper mapper;

    public BaixarEstoqueCustomizadoHandler(BaixarQtdCustomizadaCasoDeUso baixarQtdCustomizadaCasoDeUso, ObjectMapper mapper) {
        this.baixarQtdCustomizadaCasoDeUso = baixarQtdCustomizadaCasoDeUso;
        this.mapper = mapper;
    }

    @Override
    public TipoEvento tipoEvento() {
        return TipoEvento.BAIXAR_ESTOQUE_ASSOCIACAO;
    }

    @Override
    public void processar(OutboxEvento outboxEvento){
        try {
            PedidoCriadoPayload payload = mapper.readValue(outboxEvento.getPayload(), PedidoCriadoPayload.class);
            baixarQtdCustomizadaCasoDeUso.executar(payload.idCardapio(), payload.itens());
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao processar Outbox id= " + outboxEvento.getId() + "| Exception:  " +  e.getMessage());
        }
    }
}
