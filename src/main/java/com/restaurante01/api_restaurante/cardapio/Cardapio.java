package com.restaurante01.api_restaurante.cardapio;

import com.restaurante01.api_restaurante.cardapioproduto.CardapioProduto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;


//um cardapio pode ter nenhum ou varios produtos
//cardapio por ter variações personalizadass, por exemplo cardapios promocionais

@Entity
@Table(name = "CARDAPIOS")
public class Cardapio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String nome;
    @NotNull
    private String descricao;
    @NotNull
    private boolean disponibilidade;

    @Enumerated(EnumType.STRING)
    private TipoCardapio tipo;

    public enum TipoCardapio {
        NORMAL,
        PROMOCIONAL,
        SAZONAL,
        PERSONALIZADO
    }

    @OneToMany(mappedBy = "cardapio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardapioProduto> produtos;


    public Cardapio(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public List<CardapioProduto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<CardapioProduto> produtos) {
        this.produtos = produtos;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public TipoCardapio getTipo() {
        return tipo;
    }

    public void setTipo(TipoCardapio tipo) {
        this.tipo = tipo;
    }
}
