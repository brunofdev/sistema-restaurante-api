package com.restaurante01.api_restaurante.cardapioproduto.service;


import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioProdutoDTO;
import com.restaurante01.api_restaurante.cardapioproduto.mapper.CardapioProdutoMapper;
import com.restaurante01.api_restaurante.cardapioproduto.repository.CardapioProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardapioProdutoService {

    private final CardapioProdutoRepository cardapioProdutoRepository;
    private final CardapioProdutoMapper cardapioProdutoMapper;

    @Autowired
    public CardapioProdutoService(CardapioProdutoRepository cardapioProdutoRepository, CardapioProdutoMapper cardapioProdutoMapper){
        this.cardapioProdutoRepository = cardapioProdutoRepository;
        this.cardapioProdutoMapper = cardapioProdutoMapper;
    }

    public List<CardapioProdutoDTO> listarCardapiosProdutos(){
        return cardapioProdutoMapper.mapearCardapioComProduto(cardapioProdutoRepository.findAll());
    }
}
