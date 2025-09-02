package com.restaurante01.api_restaurante.produto.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;

    @NotBlank(message = "Descrição não pode ser vazia")
    private String descricao;

    @NotNull(message = "Quantidade deve ser zero ou positivo")
    @PositiveOrZero(message = "preço deve ser positivo")
    private Double preco;


    @Min(value = 0, message = "Quantidade minima deve ser zero")
    private Long quantidadeAtual;

    private Boolean disponibilidade;

    public Produto(){

    }
    public Produto(Long id,String nome, String descricao, Double preco, Long quantidadeAtual, Boolean disponibilidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeAtual = quantidadeAtual;
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

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }


    public void setDisponibilidade(Boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public Boolean getDisponibilidade() {
        return disponibilidade;
    }

    public Long getQuantidadeAtual() {
        return quantidadeAtual;
    }

    public void setQuantidadeAtual(Long quantidadeAtual) {
        this.quantidadeAtual = quantidadeAtual;
    }
}
