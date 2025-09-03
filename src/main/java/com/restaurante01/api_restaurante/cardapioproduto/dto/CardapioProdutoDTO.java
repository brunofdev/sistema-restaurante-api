package com.restaurante01.api_restaurante.cardapioproduto.dto;

import com.restaurante01.api_restaurante.cardapio.dto.CardapioDTO;
import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoDTO;

public class CardapioProdutoDTO {
    private Long id;
    private CardapioDTO cardapio;
    private ProdutoDTO produto;
    private Double precoCustomizado;
    private Integer quantidadeCustomizada;
    private String descricaoCustomizada;
    private Boolean disponibilidadeCustomizada;
    private String observacao;

    public CardapioProdutoDTO() {
    }

    public CardapioProdutoDTO(Long id, CardapioDTO cardapio, ProdutoDTO produto, Double precoCustomizado,
                              Integer quantidadeCustomizada, String descricaoCustomizada,
                              Boolean disponibilidadeCustomizada, String observacao) {
        this.id = id;
        this.cardapio = cardapio;
        this.produto = produto;
        this.precoCustomizado = precoCustomizado;
        this.quantidadeCustomizada = quantidadeCustomizada;
        this.descricaoCustomizada = descricaoCustomizada;
        this.disponibilidadeCustomizada = disponibilidadeCustomizada;
        this.observacao = observacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CardapioDTO getCardapio() {
        return cardapio;
    }

    public void setCardapio(CardapioDTO cardapio) {
        this.cardapio = cardapio;
    }

    public ProdutoDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoDTO produto) {
        this.produto = produto;
    }

    public Double getPrecoCustomizado() {
        return precoCustomizado;
    }

    public void setPrecoCustomizado(Double precoCustomizado) {
        this.precoCustomizado = precoCustomizado;
    }

    public Integer getQuantidadeCustomizada() {
        return quantidadeCustomizada;
    }

    public void setQuantidadeCustomizada(Integer quantidadeCustomizada) {
        this.quantidadeCustomizada = quantidadeCustomizada;
    }

    public String getDescricaoCustomizada() {
        return descricaoCustomizada;
    }

    public void setDescricaoCustomizada(String descricaoCustomizada) {
        this.descricaoCustomizada = descricaoCustomizada;
    }

    public Boolean getDisponibilidadeCustomizada() {
        return disponibilidadeCustomizada;
    }

    public void setDisponibilidadeCustomizada(Boolean disponibilidadeCustomizada) {
        this.disponibilidadeCustomizada = disponibilidadeCustomizada;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
