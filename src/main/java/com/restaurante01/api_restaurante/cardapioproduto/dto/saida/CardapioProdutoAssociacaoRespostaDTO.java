package com.restaurante01.api_restaurante.cardapioproduto.dto.saida;

import com.restaurante01.api_restaurante.cardapio.dto.saida.CardapioDTO;

import com.restaurante01.api_restaurante.produto.dto.entrada.ProdutoDTO;

import java.math.BigDecimal;

public class CardapioProdutoAssociacaoRespostaDTO {
    private String message;
    private CardapioDTO cardapioDTO;
    private ProdutoDTO produtoDTO;
    private BigDecimal precoCustomizado;
    private Integer quantidadeCustomizada;
    private String descricaoCustomizada;
    private Boolean disponibilidadeCustomizada;
    private String observacao;

    public CardapioProdutoAssociacaoRespostaDTO(String message, CardapioDTO cardapioDTO, ProdutoDTO produtoDTO, BigDecimal precoCustomizado, Integer quantidadeCustomizada, Boolean disponibilidadeCustomizada, String observacao, String descricaoCustomizada) {
        this.message = message;
        this.cardapioDTO = cardapioDTO;
        this.produtoDTO = produtoDTO;
        this.precoCustomizado = precoCustomizado;
        this.quantidadeCustomizada = quantidadeCustomizada;
        this.disponibilidadeCustomizada = disponibilidadeCustomizada;
        this.observacao = observacao;
        this.descricaoCustomizada = descricaoCustomizada;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CardapioDTO getCardapioDTO() {
        return cardapioDTO;
    }

    public void setCardapioDTO(CardapioDTO cardapioDTO) {
        this.cardapioDTO = cardapioDTO;
    }

    public ProdutoDTO getProdutoDTO() {
        return produtoDTO;
    }

    public void setProdutoDTO(ProdutoDTO produtoDTO) {
        this.produtoDTO = produtoDTO;
    }

    public BigDecimal getPrecoCustomizado() {
        return precoCustomizado;
    }

    public void setPrecoCustomizado(BigDecimal precoCustomizado) {
        this.precoCustomizado = precoCustomizado;
    }

    public Integer getQuantidadeCustomizada() {
        return quantidadeCustomizada;
    }

    public void setQuantidadeCustomizada(Integer quantidadeCustomizada) {
        this.quantidadeCustomizada = quantidadeCustomizada;
    }

    public Boolean getDisponibilidadeCustomizada() {
        return disponibilidadeCustomizada;
    }

    public void setDisponibilidadeCustomizada(Boolean disponibilidadeCustomizada) {
        this.disponibilidadeCustomizada = disponibilidadeCustomizada;
    }

    public String getDescricaoCustomizada() {
        return descricaoCustomizada;
    }

    public void setDescricaoCustomizada(String descricaoCustomizada) {
        this.descricaoCustomizada = descricaoCustomizada;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
