package com.restaurante01.api_restaurante.cardapioproduto.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurante01.api_restaurante.produto.dto.ProdutoCustomDTO;


import java.time.LocalDate;
import java.util.List;


public class CardapioProdutoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Boolean disponibilidade;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    private List<ProdutoCustomDTO> produtos;

    public CardapioProdutoDTO(){};
    public CardapioProdutoDTO(Long id, String nome, String descricao, Boolean disponibilidade,
                              LocalDate dataInicio, LocalDate dataFim, List<ProdutoCustomDTO> produtos) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.disponibilidade = disponibilidade;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.produtos = produtos;
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

    public Boolean getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(Boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public List<ProdutoCustomDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoCustomDTO> produtos) {
        this.produtos = produtos;
    }
}
