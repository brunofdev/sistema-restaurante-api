package com.restaurante01.api_restaurante.produto.dto.entrada;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ProdutoCreateDTO {
    @NotBlank(message = "Nome não deve ser vazio")
    private String nome;
    @NotBlank(message = "Descrição não deve ser vazia")
    private String descricao;
    @NotNull(message = "Preço deve ser informado")
    @PositiveOrZero(message = "Preço deve ser zero ou positivo")
    private BigDecimal preco;
    @NotNull(message = "Quantidade deve ser zero ou positivo")
    @Min(value = 0, message = "Quantidade minima deve ser zero")
    private Long quantidadeAtual;
    private Boolean disponibilidade;

    public ProdutoCreateDTO(String nome, String descricao, BigDecimal preco, Long quantidadeAtual, Boolean disponibilidade) {
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

    public BigDecimal getPreco() {
        return preco;
    }

    public String getNome() {
        return nome;
    }
}


