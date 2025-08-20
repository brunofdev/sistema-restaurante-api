package com.restaurante01.api_restaurante.cardapioproduto.dto;


public class CamposCustomDTO {
    private Double precoCustomizado;
    private Integer quantidadeCustomizada;
    private String descricaoCustomizada;
    private Boolean disponibilidadeCustomizada;
    private String observacao;

    public CamposCustomDTO(
                              Double precoCustomizado, Integer quantidadeCustomizada,
                              String descricaoCustomizada, Boolean disponibilidadeCustomizada,
                              String observacao) {
        this.precoCustomizado = precoCustomizado;
        this.quantidadeCustomizada = quantidadeCustomizada;
        this.descricaoCustomizada = descricaoCustomizada;
        this.disponibilidadeCustomizada = disponibilidadeCustomizada;
        this.observacao = observacao;
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
