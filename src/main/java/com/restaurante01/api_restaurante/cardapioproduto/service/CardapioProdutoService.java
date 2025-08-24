package com.restaurante01.api_restaurante.cardapioproduto.service;


import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.cardapio.mapper.CardapioMapper;
import com.restaurante01.api_restaurante.cardapio.service.CardapioService;
import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioProdutoDTO;
import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioProdutoSaveDTO;
import com.restaurante01.api_restaurante.cardapioproduto.entity.CardapioProduto;
import com.restaurante01.api_restaurante.cardapioproduto.mapper.CardapioProdutoMapper;
import com.restaurante01.api_restaurante.cardapioproduto.repository.CardapioProdutoRepository;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import com.restaurante01.api_restaurante.produto.mapper.ProdutoMapper;
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
    private final ProdutoMapper produtoMapper;
    private final CardapioMapper cardapioMapper;

    @Autowired
    public CardapioProdutoService(CardapioProdutoRepository cardapioProdutoRepository, CardapioProdutoMapper cardapioProdutoMapper,
                                  ProdutoService produtoService, CardapioService cardapioService, ProdutoMapper produtoMapper,
                                  CardapioMapper cardapioMapper){
        this.cardapioProdutoRepository = cardapioProdutoRepository;
        this.cardapioProdutoMapper = cardapioProdutoMapper;
        this.produtoService = produtoService;
        this.cardapioService = cardapioService;
        this.produtoMapper = produtoMapper;
        this.cardapioMapper = cardapioMapper;
    }

    public List<CardapioProdutoDTO> listarCardapiosProdutos(){
        return cardapioProdutoMapper.mapearCardapioComProduto(cardapioProdutoRepository.findAll());
    }
    public CardapioProdutoSaveDTO associarProdutoCardapio(Long idProduto, Long idCardapio){
        Produto produto = produtoMapper.mapearUmaDtoParaEntidade(produtoService.listarUmProdutoPorId(idProduto));
        Cardapio cardapio = cardapioMapper.mapearUmaDtoParaEntidade(cardapioService.listarUmCardapio(idCardapio));
        cardapioProdutoRepository.save(cardapioProdutoMapper.instanciarCardapioProduto(produto, cardapio));
        CardapioProdutoSaveDTO cardapioProdutoSaveDTO = cardapioProdutoMapper.instanciarCardapioProdutoSaveDTO(
                produtoMapper.mapearUmaEntidadeParaDTO(produto),
                cardapioMapper.mapearUmaEntidadeParaDTO(cardapio));
        return cardapioProdutoSaveDTO;


    }
}
