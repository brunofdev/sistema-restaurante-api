package com.restaurante01.api_restaurante.builders;

import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;

import java.math.BigDecimal;

public class ProdutoBuilder {

    private Long id = 1L;
    private String nome = "Produto Teste";
    private String descricao = "Descrição do produto teste";
    private BigDecimal preco = BigDecimal.valueOf(10.00);
    private Long quantidadeAtual = 10L;
    private Boolean disponibilidade = true;

    // --- FACTORY ---
    public static ProdutoBuilder umProduto() {
        return new ProdutoBuilder();
    }

    // --- FLUENT API ---
    public ProdutoBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ProdutoBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ProdutoBuilder comDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public ProdutoBuilder comPreco(BigDecimal preco) {
        this.preco = preco;
        return this;
    }

    public ProdutoBuilder comQuantidade(Long quantidade) {
        this.quantidadeAtual = quantidade;
        return this;
    }

    public ProdutoBuilder disponivel() {
        this.disponibilidade = true;
        return this;
    }

    public ProdutoBuilder indisponivel() {
        this.disponibilidade = false;
        return this;
    }

    // --- BUILD ---
    public Produto build() {
        Produto produto = new Produto();

        produto.setId(id);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setQuantidadeAtual(quantidadeAtual);
        produto.setDisponibilidade(disponibilidade);

        return produto;
    }
}