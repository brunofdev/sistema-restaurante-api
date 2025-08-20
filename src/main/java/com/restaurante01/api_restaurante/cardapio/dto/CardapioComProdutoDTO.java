package com.restaurante01.api_restaurante.cardapio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurante01.api_restaurante.produto.dto.ProdutoComCamposCustomDTO;


import java.time.LocalDate;
import java.util.List;

public class CardapioComProdutoDTO {
    private final Long id;
    private final String nome;
    private final String descricao;
    private final Boolean disponibilidade;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate dataInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate dataFim;

    List<ProdutoComCamposCustomDTO> produtos;
    public CardapioComProdutoDTO(Long id, String nome, String descricao,
                                 Boolean disponibilidade, LocalDate dataInicio,
                                 LocalDate dataFim, List<ProdutoComCamposCustomDTO> produtos ) {
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
    public String getNome() {
        return nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public LocalDate getDataInicio() {
        return dataInicio;
    }
    public Boolean getDisponibilidade() {
        return disponibilidade;
    }
    public LocalDate getDataFim() {
        return dataFim;
    }
    public List<ProdutoComCamposCustomDTO> getProdutos() {
        return produtos;
    }
    public void setProdutos(List<ProdutoComCamposCustomDTO> produtos) {
        this.produtos = produtos;
    }
}
