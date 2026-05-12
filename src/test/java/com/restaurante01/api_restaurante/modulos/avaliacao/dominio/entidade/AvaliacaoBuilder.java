package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoBuilder {
    private Long pedidoId = 100L;
    private Long clienteId = 200L;
    private List<AvaliacaoItem> itens = new ArrayList<>();

    private AvaliacaoBuilder() {
        this.itens.add(AvaliacaoItem.criar(1L, "Hamburguer Clássico", null, null));
    }

    public static AvaliacaoBuilder umaAvaliacao() {
        return new AvaliacaoBuilder();
    }

    public AvaliacaoBuilder semPedido() {
        this.pedidoId = null;
        return this;
    }

    public AvaliacaoBuilder semCliente() {
        this.clienteId = null;
        return this;
    }

    public AvaliacaoBuilder semItens() {
        this.itens = new ArrayList<>(); // Lista vazia
        return this;
    }

    public Avaliacao construir() {
        return Avaliacao.criar(this.pedidoId, this.clienteId, this.itens);
    }
}