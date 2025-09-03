package com.restaurante01.api_restaurante.produto.dto.entrada;

import jakarta.validation.constraints.*;

public class ProdutoDTO {
        @NotNull(message = "Preço deve ser informado")
        @Positive
        private Long id;
        @NotBlank(message = "Nome não deve ser vazio")
        private String nome;
        @NotBlank(message = "Descrição não deve ser vazia")
        private String descricao;
        @NotNull(message = "Preço deve ser informado")
        @PositiveOrZero(message = "Preço deve ser zero ou positivo")
        private Double preco;
        @NotNull(message = "Quantidade deve ser zero ou positivo")
        @Min(value = 0, message = "Quantidade minima deve ser zero")
        private Long quantidadeAtual;
        private Boolean disponibilidade;


    public ProdutoDTO(Long id,String nome, String descricao, Double preco, Long quantidadeAtual, Boolean disponibilidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeAtual = quantidadeAtual;
        this.disponibilidade = disponibilidade;
    }
    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Long getQuantidadeAtual() {
        return quantidadeAtual;
    }

    public void setQuantidadeAtual(Long quantidadeAtual) {
        this.quantidadeAtual = quantidadeAtual;
    }

    public Boolean getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(Boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


}
