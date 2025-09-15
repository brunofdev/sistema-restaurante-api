package com.restaurante01.api_restaurante.cardapioproduto.dto.entrada;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CardapioProdutoAssociacaoEntradaDTO {
    @NotNull(message = "ID do cardapio deve ser enviada")
    private Long idCardapio;
    @NotNull (message = "ID do produto deve ser enviada")
    private Long idProduto;
    @Min(0)
    private Double precoCustomizado;
    @Min(0)
    private Integer quantidadeCustomizada;
    private String descricaoCustomizada;
    private Boolean disponibilidadeCustomizada;
    private String observacao;

    public CardapioProdutoAssociacaoEntradaDTO(){}

    public CardapioProdutoAssociacaoEntradaDTO(Long idCardapio, Long idProduto,Double precoCustomizado, Integer quantidadeCustomizada, String descricaoCustomizada, Boolean disponibilidadeCustomizada, String observacao) {
        this.idCardapio = idCardapio;
        this.idProduto = idProduto;
        this.precoCustomizado = precoCustomizado;
        this.quantidadeCustomizada = quantidadeCustomizada;
        this.descricaoCustomizada = descricaoCustomizada;
        this.disponibilidadeCustomizada = disponibilidadeCustomizada;
        this.observacao = observacao;
    }

    public Long getIdCardapio() {
        return idCardapio;
    }

    public void setIdCardapio(Long idCardapio) {
        this.idCardapio = idCardapio;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
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
