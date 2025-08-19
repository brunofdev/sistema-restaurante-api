package com.restaurante01.api_restaurante.cardapioproduto.service;


import com.restaurante01.api_restaurante.cardapioproduto.entity.CardapioProduto;
import com.restaurante01.api_restaurante.cardapioproduto.repository.CardapioProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardapioProdutoService {

    private final CardapioProdutoRepository cardapioProdutoRepository;

    @Autowired
    public CardapioProdutoService(CardapioProdutoRepository cardapioProdutoRepository){
        this.cardapioProdutoRepository = cardapioProdutoRepository;
    }

    public List<CardapioProduto> listarCardapiosProdutos(){
        return cardapioProdutoRepository.findAll();
    }
}
