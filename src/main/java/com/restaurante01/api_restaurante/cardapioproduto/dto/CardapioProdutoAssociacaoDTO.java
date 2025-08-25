package com.restaurante01.api_restaurante.cardapioproduto.dto;

import com.restaurante01.api_restaurante.cardapio.dto.CardapioDTO;

import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.produto.dto.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.entity.Produto;

public class CardapioProdutoAssociacaoDTO {
    private String message;
    private CardapioDTO cardapioDTO;
    private ProdutoDTO produtoDTO;

    public CardapioProdutoAssociacaoDTO() {
    }

    public CardapioProdutoAssociacaoDTO(String message, ProdutoDTO produtoDTO, CardapioDTO cardapioDTO) {
        this.message = message;
        this.produtoDTO = produtoDTO;
        this.cardapioDTO = cardapioDTO;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProdutoDTO getProdutoDTO() {
        return produtoDTO;
    }

    public void setProdutoDTO(ProdutoDTO produtoDTO) {
        this.produtoDTO = produtoDTO;
    }

    public CardapioDTO getCardapioDTO() {
        return cardapioDTO;
    }

    public void setCardapioDTO(CardapioDTO cardapioDTO) {
        this.cardapioDTO = cardapioDTO;
    }
}
