package com.restaurante01.api_restaurante.cardapioproduto.controller;


import com.restaurante01.api_restaurante.cardapioproduto.entity.CardapioProduto;
import com.restaurante01.api_restaurante.cardapioproduto.service.CardapioProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cardapioproduto")
public class CardapioProdutoController {

    @Autowired private CardapioProdutoService cardapioProdutoService;

    @GetMapping("/obter-todos-cardapios")
    public ResponseEntity<List<CardapioProduto>> listarCardapioProdutos(){
        return ResponseEntity.ok(cardapioProdutoService.listarCardapiosProdutos());
    }
}
