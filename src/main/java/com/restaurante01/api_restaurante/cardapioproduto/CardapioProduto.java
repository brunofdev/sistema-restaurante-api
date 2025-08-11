package com.restaurante01.api_restaurante.cardapioproduto;


//Esta classe faz um intermediario entre produto e cardapio, para poder ter variações de preços e quantidades.

import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "CARDAPIO_PRODUTOS")
public class CardapioProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cardapio_id")
    private Cardapio cardapio;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @NotNull
    private Double precoPromocional;

    @NotNull
    private int quantidadeDisponivel;

    private String descricaoPromocional;

    @NotNull
    private String observacao;

    public CardapioProduto(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cardapio getCardapio() {
        return cardapio;
    }

    public void setCardapio(Cardapio cardapio) {
        this.cardapio = cardapio;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getPreco() {
        return precoPromocional;
    }

    public void setPreco(Double preco) {
        this.precoPromocional = precoPromocional;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Double getPrecoPromocional() {
        return precoPromocional;
    }

    public void setPrecoPromocional(Double precoPromocional) {
        this.precoPromocional = precoPromocional;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public String getDescricaoPromocional() {
        return descricaoPromocional;
    }

    public void setDescricaoPromocional(String descricaoPromocional) {
        this.descricaoPromocional = descricaoPromocional;
    }
}
