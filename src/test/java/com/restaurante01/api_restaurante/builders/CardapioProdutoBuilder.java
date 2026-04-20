package com.restaurante01.api_restaurante.builders;

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Associacao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;

import java.math.BigDecimal;

public class CardapioProdutoBuilder {

    private Long id = 1L;

    private Cardapio cardapio = CardapioBuilder.umCardapio().build();
    private Produto produto = ProdutoBuilder.umProduto().build();

    private BigDecimal precoCustomizado = BigDecimal.valueOf(15.00);
    private Integer quantidadeCustomizada = 10;
    private String descricaoCustomizada = "Descrição customizada";
    private Boolean disponibilidadeCustomizada = true;
    private String observacao = "Sem observações";

    // --- FACTORY ---
    public static CardapioProdutoBuilder umCardapioProduto() {
        return new CardapioProdutoBuilder();
    }

    // --- FLUENT API ---
    public CardapioProdutoBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public CardapioProdutoBuilder comCardapio(Cardapio cardapio) {
        this.cardapio = cardapio;
        return this;
    }

    public CardapioProdutoBuilder comProduto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public CardapioProdutoBuilder comPrecoCustomizados(BigDecimal preco) {
        this.precoCustomizado = preco;
        return this;
    }

    public CardapioProdutoBuilder comQuantidadeCustomizada(Integer quantidade) {
        this.quantidadeCustomizada = quantidade;
        return this;
    }

    public CardapioProdutoBuilder semQuantidadeCustomizada() {
        this.quantidadeCustomizada = null;
        return this;
    }

    public CardapioProdutoBuilder disponivel() {
        this.disponibilidadeCustomizada = true;
        return this;
    }

    public CardapioProdutoBuilder indisponivel() {
        this.disponibilidadeCustomizada = false;
        return this;
    }

    public CardapioProdutoBuilder comDescricao(String descricao) {
        this.descricaoCustomizada = descricao;
        return this;
    }

    public CardapioProdutoBuilder comObservacao(String obs) {
        this.observacao = obs;
        return this;
    }

    // --- BUILD ---
    public Associacao build() {
        Associacao entity = new Associacao();

        entity.setId(id);
        entity.setCardapio(cardapio);
        entity.setProduto(produto);
        entity.setPrecoCustomizado(precoCustomizado);
        entity.setQuantidadeCustomizada(quantidadeCustomizada);
        entity.setDescricaoCustomizada(descricaoCustomizada);
        entity.setDisponibilidadeCustomizada(disponibilidadeCustomizada);
        entity.setObservacao(observacao);

        return entity;
    }
}