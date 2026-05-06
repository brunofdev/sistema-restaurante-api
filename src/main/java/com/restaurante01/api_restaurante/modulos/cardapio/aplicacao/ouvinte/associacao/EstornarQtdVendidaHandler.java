package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.ouvinte.associacao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante01.api_restaurante.compartilhado.aplicacao.OutboxEventoHandler;
import com.restaurante01.api_restaurante.compartilhado.dominio.entidade.OutboxEvento;
import com.restaurante01.api_restaurante.compartilhado.dominio.enums.TipoEvento;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso.EstornarProdutoVendidoCasoDeUso;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.PedidoCriadoPayload;
import org.springframework.stereotype.Component;

@Component
public class EstornarQtdVendidaHandler implements OutboxEventoHandler {

    private final EstornarProdutoVendidoCasoDeUso estornarProdutoVendidoCasoDeUso;
    private final ObjectMapper objectMapper;

    public EstornarQtdVendidaHandler(EstornarProdutoVendidoCasoDeUso estornarProdutoVendidoCasoDeUso, ObjectMapper objectMapper) {
        this.estornarProdutoVendidoCasoDeUso = estornarProdutoVendidoCasoDeUso;
        this.objectMapper = objectMapper;
    }

    @Override
    public TipoEvento tipoEvento() {
        return TipoEvento.ESTORNAR_ESTOQUE_ASSOCIACAO;
    }

    @Override
    public void processar(OutboxEvento outboxEvento){
        try {
            PedidoCriadoPayload itensParaEstornar = objectMapper.readValue(outboxEvento.getPayload(), PedidoCriadoPayload.class);
            estornarProdutoVendidoCasoDeUso.executar(itensParaEstornar.itens(), itensParaEstornar.idCardapio());
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao processar Outbox id= " + outboxEvento.getId() + "| Exception:  " +  e.getMessage());
        }
    }
}
