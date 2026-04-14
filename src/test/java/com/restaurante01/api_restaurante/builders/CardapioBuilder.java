package com.restaurante01.api_restaurante.builders;

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;

import java.time.LocalDate;

public class CardapioBuilder {

    private Long id = 1L;
    private String nome = "Cardápio Teste";
    private String descricao = "Descrição do cardápio";
    private Boolean disponibilidade = true;
    private LocalDate dataInicio = LocalDate.now().minusDays(1);
    private LocalDate dataFim = LocalDate.now().plusDays(10);

    // --- FACTORY ---
    public static CardapioBuilder umCardapio() {
        return new CardapioBuilder();
    }

    // --- FLUENT API ---
    public CardapioBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public CardapioBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public CardapioBuilder comDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public CardapioBuilder disponivel() {
        this.disponibilidade = true;
        return this;
    }

    public CardapioBuilder indisponivel() {
        this.disponibilidade = false;
        return this;
    }

    public CardapioBuilder comDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
        return this;
    }

    public CardapioBuilder comDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
        return this;
    }

    // --- BUILD ---
    public Cardapio build() {
        Cardapio cardapio = new Cardapio();

        cardapio.setId(id);
        cardapio.setNome(nome);
        cardapio.setDescricao(descricao);
        cardapio.setDisponibilidade(disponibilidade);
        cardapio.setDataInicio(dataInicio);
        cardapio.setDataFim(dataFim);

        return cardapio;
    }
}