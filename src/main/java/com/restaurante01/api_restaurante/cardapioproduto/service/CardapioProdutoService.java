package com.restaurante01.api_restaurante.cardapioproduto.service;



import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.cardapio.service.CardapioService;
import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioProdutoAssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioComListaProdutoDTO;
import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioProdutoAssociacaoRespostaDTO;
import com.restaurante01.api_restaurante.cardapioproduto.entity.CardapioProduto;
import com.restaurante01.api_restaurante.cardapioproduto.mapper.CardapioProdutoMapper;
import com.restaurante01.api_restaurante.cardapioproduto.repository.CardapioProdutoRepository;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import com.restaurante01.api_restaurante.produto.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardapioProdutoService {

    private final CardapioProdutoRepository cardapioProdutoRepository;
    private final CardapioProdutoMapper cardapioProdutoMapper;
    private final ProdutoService produtoService;
    private final CardapioService cardapioService;


    @Autowired
    public CardapioProdutoService(CardapioProdutoRepository cardapioProdutoRepository,
                                  CardapioProdutoMapper cardapioProdutoMapper,
                                  ProdutoService produtoService, CardapioService cardapioService
                                 ) {
        this.cardapioProdutoRepository = cardapioProdutoRepository;
        this.cardapioProdutoMapper = cardapioProdutoMapper;
        this.produtoService = produtoService;
        this.cardapioService = cardapioService;

    }

    public List<CardapioComListaProdutoDTO> listarCardapiosProdutos() {
        return cardapioProdutoMapper.mapearCardapioComListaDeProduto(cardapioProdutoRepository.findAll());
    }

    public CardapioProdutoAssociacaoRespostaDTO associarProdutoAoCardapio(CardapioProdutoAssociacaoEntradaDTO cardapioProdutoAssociacaoEntradaDTO) {
        Produto produto = produtoService.buscarProdutoPorId(cardapioProdutoAssociacaoEntradaDTO.getIdProduto()); /*Pode ocorrer exception aqui*/
        Cardapio cardapio = cardapioService.buscarCardapioPorId(cardapioProdutoAssociacaoEntradaDTO.getIdCardapio());/*Pode ocorrer exception aqui*/
        CardapioProduto cardapioProduto = cardapioProdutoMapper.mapearCardapioProduto(produto, cardapio, cardapioProdutoAssociacaoEntradaDTO);
        cardapioProdutoRepository.save(cardapioProduto);
        return cardapioProdutoMapper.mapearCardapioProdutoAssociacaoDTO(cardapioProduto);
    }
}