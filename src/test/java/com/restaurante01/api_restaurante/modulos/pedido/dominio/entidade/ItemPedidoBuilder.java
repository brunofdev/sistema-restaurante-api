package com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade;

import com.restaurante01.api_restaurante.builders.ProdutoBuilder;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.RepresentacaoProdutoItemPedido;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;

import java.math.BigDecimal;

public class ItemPedidoBuilder {

    private Long id = 1L;

    private Pedido pedido = null;
    private Produto produto = ProdutoBuilder.umProduto().build();

    private Integer quantidade = 1;
    private BigDecimal precoUnitario = BigDecimal.valueOf(10.00);
    private String observacao = "Sem observações";

    // --- FACTORY ---
    public static ItemPedidoBuilder umItemPedido() {
        return new ItemPedidoBuilder();
    }

    // --- FLUENT API ---
    public ItemPedidoBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ItemPedidoBuilder comPedido(Pedido pedido) {
        this.pedido = pedido;
        return this;
    }

    public ItemPedidoBuilder comProduto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public ItemPedidoBuilder comQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public ItemPedidoBuilder comPrecoUnitario(BigDecimal preco) {
        this.precoUnitario = preco;
        return this;
    }

    public ItemPedidoBuilder comObservacao(String obs) {
        this.observacao = obs;
        return this;
    }

    public ItemPedidoBuilder semObservacao() {
        this.observacao = null;
        return this;
    }

    public ItemPedido build() {
        return new ItemPedido(id, pedido, new RepresentacaoProdutoItemPedido(produto.getId(), produto.getNome()), quantidade, precoUnitario, observacao);
    }
}