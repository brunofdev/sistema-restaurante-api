package com.restaurante01.api_restaurante.produto.dto;

public class ProdutoCreateDTO {
    private String nome;
    private String descricao;
    private Double preco;
    private Long quantidadeAtual;
    private Boolean disponibilidade;

    public ProdutoCreateDTO(String nome, String descricao, Double preco, Long quantidadeAtual, Boolean disponibilidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeAtual = quantidadeAtual;
        this.disponibilidade = disponibilidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getQuantidadeAtual() {
        return quantidadeAtual;
    }

    public Boolean getDisponibilidade() {
        return disponibilidade;
    }

    public Double getPreco() {
        return preco;
    }

    public String getNome() {
        return nome;
    }
}


