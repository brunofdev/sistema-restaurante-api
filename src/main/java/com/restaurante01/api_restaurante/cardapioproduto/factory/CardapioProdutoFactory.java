package com.restaurante01.api_restaurante.cardapioproduto.factory;

import com.restaurante01.api_restaurante.cardapio.dto.CardapioDTO;
import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioProdutoAssociacaoDTO;
import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioComListaProdutoDTO;

import com.restaurante01.api_restaurante.cardapioproduto.entity.CardapioProduto;
import com.restaurante01.api_restaurante.produto.dto.ProdutoCustomDTO;
import com.restaurante01.api_restaurante.produto.dto.ProdutoDTO;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CardapioProdutoFactory {
    public CardapioProdutoAssociacaoDTO criarCardapioProdutoAssociacaoDTO(ProdutoDTO produto, CardapioDTO cardapio){
        CardapioProdutoAssociacaoDTO CardapioProdutoAssociacaoDTO = new CardapioProdutoAssociacaoDTO();
        CardapioProdutoAssociacaoDTO.setMessage("O produto: " + produto.getNome() + " foi associado ao cardapio: " + cardapio.getNome());
        CardapioProdutoAssociacaoDTO.setProduto(produto);
        CardapioProdutoAssociacaoDTO.setCardapio(cardapio);
        return  CardapioProdutoAssociacaoDTO;
    }
    public CardapioProduto criarAssociacaoCardapioProduto(Produto produto, Cardapio cardapio){
        CardapioProduto cardapioProduto = new CardapioProduto();
        cardapioProduto.setProduto(produto);
        cardapioProduto.setCardapio(cardapio);
        return cardapioProduto;
    }
    public CardapioComListaProdutoDTO criarCardapioProdutoDTO(Cardapio cardapio) {
        CardapioComListaProdutoDTO cardapioProdutoDTO = new CardapioComListaProdutoDTO();
        cardapioProdutoDTO.setId(cardapio.getId());
        cardapioProdutoDTO.setNome(cardapio.getNome());
        cardapioProdutoDTO.setDescricao(cardapio.getDescricao());
        cardapioProdutoDTO.setDisponibilidade(cardapio.getDisponibilidade());
        cardapioProdutoDTO.setDataInicio(cardapio.getDataInicio());
        cardapioProdutoDTO.setDataFim(cardapio.getDataFim());
        cardapioProdutoDTO.setProdutos(new ArrayList<>());
        return cardapioProdutoDTO;
    }
    public ProdutoCustomDTO criarProdutoCustomDTO (Produto produto, CardapioProduto cardapioProduto){
        ProdutoCustomDTO produtoCustomDTO = new ProdutoCustomDTO();
        produtoCustomDTO.setIdProduto(produto.getId());
        produtoCustomDTO.setNome(produto.getNome());
        produtoCustomDTO.setDescricao(produto.getDescricao());
        produtoCustomDTO.setPreco(produto.getPreco());
        produtoCustomDTO.setQuantidadeAtual(produto.getQuantidadeAtual());
        produtoCustomDTO.setDisponibilidade(produto.getDisponibilidade());
        produtoCustomDTO.setPrecoCustomizado(cardapioProduto.getPrecoCustomizado());
        produtoCustomDTO.setDescricaoCustomizada(cardapioProduto.getDescricaoCustomizada());
        produtoCustomDTO.setQuantidadeCustomizada(cardapioProduto.getQuantidadeCustomizada());
        produtoCustomDTO.setDisponibilidadeCustomizada(cardapioProduto.getDisponibilidadeCustomizada());
        produtoCustomDTO.setObservacao(cardapioProduto.getObservacao());
        return produtoCustomDTO;
    }

}
