package com.restaurante01.api_restaurante.produto;

public class ProdutoDTO {
        private Long id;
        private String nome;
        private String descricao;
        private double preco;
        private long quantidadeAtual;
        private boolean disponibilidade;


    public ProdutoDTO(Long id,String nome, String descricao, double preco, long quantidadeAtual, boolean disponibilidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeAtual = quantidadeAtual;
        this.disponibilidade = disponibilidade;
    }
    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public long getQuantidadeAtual() {
        return quantidadeAtual;
    }

    public void setQuantidadeAtual(long quantidadeAtual) {
        this.quantidadeAtual = quantidadeAtual;
    }

    public boolean getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
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


};
