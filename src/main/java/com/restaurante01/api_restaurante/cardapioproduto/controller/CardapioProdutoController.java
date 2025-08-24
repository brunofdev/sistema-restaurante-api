package com.restaurante01.api_restaurante.cardapioproduto.controller;


import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioProdutoDTO;
import com.restaurante01.api_restaurante.cardapioproduto.entity.CardapioProduto;
import com.restaurante01.api_restaurante.cardapioproduto.service.CardapioProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cardapioproduto")
public class CardapioProdutoController {

    @Autowired private CardapioProdutoService cardapioProdutoService;

    @GetMapping("/obter-todos-cardapios")
    public ResponseEntity<List<CardapioProdutoDTO>> listarCardapioProdutos(){
        return ResponseEntity.ok(cardapioProdutoService.listarCardapiosProdutos());
    }
    @PostMapping("/associar-produto-a-cardapio")
    public ResponseEntity<CardapioProduto> associarProdutoCardapio(@RequestBody CardapioProduto cardapioProduto){
        return ResponseEntity.ok(cardapioProdutoService.associarProdutoCardapio(cardapioProduto));
    }
}
