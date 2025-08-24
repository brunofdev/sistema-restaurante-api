package com.restaurante01.api_restaurante.cardapioproduto.dto;

import com.restaurante01.api_restaurante.cardapio.dto.CardapioDTO;

import com.restaurante01.api_restaurante.produto.dto.ProdutoDTO;


import java.time.LocalDate;

public class CardapioProdutoSaveDTO {
    private String message;
    private ProdutoDTO produto;
    private CardapioDTO cardapio;

    public CardapioProdutoSaveDTO(){};
    public CardapioProdutoSaveDTO(String message, ProdutoDTO produto, CardapioDTO cardapio) {
        this.message = message;
        this.produto = produto;
        this.cardapio = cardapio;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProdutoDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoDTO produto) {
        this.produto = produto;
    }

    public CardapioDTO getCardapio() {
        return cardapio;
    }

    public void setCardapio(CardapioDTO cardapio) {
        this.cardapio = cardapio;
    }
}
