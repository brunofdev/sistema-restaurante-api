package com.restaurante01.api_restaurante.produto.dto;

import jakarta.validation.constraints.*;

public class ProdutoCreateDTO {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotNull
    @PositiveOrZero
    private Double preco;
    @NotNull
    @Min(0)
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


