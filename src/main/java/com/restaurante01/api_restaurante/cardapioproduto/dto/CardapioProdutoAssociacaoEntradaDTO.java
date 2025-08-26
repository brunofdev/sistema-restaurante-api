package com.restaurante01.api_restaurante.cardapioproduto.dto;

public class CardapioProdutoAssociacaoEntradaDTO {
    private long idCardapio;
    private long idProduto;
    private Double precoCustomizado;
    private Integer quantidadeCustomizada;
    private String descricaoCustomizada;
    private Boolean disponibilidadeCustomizada;
    private String observacao;

    public CardapioProdutoAssociacaoEntradaDTO(long idCardapio, long idProduto,Double precoCustomizado, Integer quantidadeCustomizada, String descricaoCustomizada, Boolean disponibilidadeCustomizada, String observacao) {
        this.idCardapio = idCardapio;
        this.idProduto = idProduto;
        this.precoCustomizado = precoCustomizado;
        this.quantidadeCustomizada = quantidadeCustomizada;
        this.descricaoCustomizada = descricaoCustomizada;
        this.disponibilidadeCustomizada = disponibilidadeCustomizada;
        this.observacao = observacao;
    }

    public long getIdCardapio() {
        return idCardapio;
    }

    public void setIdCardapio(long idCardapio) {
        this.idCardapio = idCardapio;
    }

    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(long idProduto) {
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
