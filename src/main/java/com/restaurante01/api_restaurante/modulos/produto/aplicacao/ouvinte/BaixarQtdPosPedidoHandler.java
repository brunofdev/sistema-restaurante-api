package com.restaurante01.api_restaurante.modulos.produto.aplicacao.ouvinte;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.compartilhado.aplicacao.OutboxEventoHandler;
import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.PedidoCriadoPayload;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso.BaixarQuantidadeProdutoCasoDeUso;
import org.springframework.stereotype.Component;

@Component
public class BaixarQtdPosPedidoHandler implements OutboxEventoHandler {

        private final BaixarQuantidadeProdutoCasoDeUso baixarQuantidadeProdutoCasoDeUso;
        private final ObjectMapper mapper;


    public BaixarQtdPosPedidoHandler(BaixarQuantidadeProdutoCasoDeUso baixarQuantidadeProdutoCasoDeUso, ObjectMapper mapper) {
        this.baixarQuantidadeProdutoCasoDeUso = baixarQuantidadeProdutoCasoDeUso;
        this.mapper = mapper;
    }

    @Override
        public TipoEvento tipoEvento() {
            return TipoEvento.BAIXAR_ESTOQUE_PRODUTO;
        }
        @Override
        public void processar(OutboxEvento outboxEvento){
            try {
                PedidoCriadoPayload payload = mapper.readValue(outboxEvento.getPayload(), PedidoCriadoPayload.class);
                baixarQuantidadeProdutoCasoDeUso.executar(payload.itens());
            }
            catch (Exception e){
                throw new RuntimeException("Erro ao processar Outbox id= " + outboxEvento.getId() + "| Exception:  " +  e.getMessage());}
    }
}
