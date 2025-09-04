package com.restaurante01.api_restaurante.cardapioproduto.entity;


//Esta classe faz um intermediario entre produto e cardapio, para poder ter variações de preços e quantidades.

import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "CARDAPIO_PRODUTO", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cardapio_id", "produto_id"})
})
public class CardapioProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cardapio_id", nullable = false)
    private Cardapio cardapio;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;


    @Column(name = "preco_customizado")
    private Double precoCustomizado;
    @Column(name = "qtd_customizada")
    private Integer quantidadeCustomizada;
    @Column(name = "desc_customizada")
    private String descricaoCustomizada;
    @Column(name = "disp_customizada")
    private Boolean disponibilidadeCustomizada;
    @Column(name = "obs")
    private String observacao;

    public CardapioProduto(){
    }

    public CardapioProduto(
                           Long id, Cardapio cardapio, Produto produto, Double precoCustomizado,
                           Integer quantidadeCustomizada, Boolean disponibilidadeCustomizada,
                           String observacao, String descricaoCustomizada)
    {
        this.cardapio = cardapio;
        this.produto = produto;
        this.precoCustomizado = precoCustomizado;
        this.quantidadeCustomizada = quantidadeCustomizada;
        this.disponibilidadeCustomizada = disponibilidadeCustomizada;
        this.observacao = observacao;
        this.descricaoCustomizada = descricaoCustomizada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CardapioProduto that = (CardapioProduto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Object equals() {
        return null;
    }
}
