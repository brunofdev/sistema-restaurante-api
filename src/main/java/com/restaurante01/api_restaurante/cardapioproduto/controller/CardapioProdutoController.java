package com.restaurante01.api_restaurante.cardapioproduto.controller;


import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioProdutoAssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioComListaProdutoDTO;
import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioProdutoAssociacaoRespostaDTO;
import com.restaurante01.api_restaurante.cardapioproduto.service.CardapioProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cardapioproduto")
public class CardapioProdutoController {

    @Autowired
    private CardapioProdutoService cardapioProdutoService;

    @GetMapping("/obter-todos-cardapios")
    public ResponseEntity<List<CardapioComListaProdutoDTO>> listarCardapioProdutos() {
        return ResponseEntity.ok(cardapioProdutoService.listarCardapiosProdutos());
    }
    @PostMapping("/associar-cardapioproduto")
    public ResponseEntity<CardapioProdutoAssociacaoRespostaDTO> associarProdutoCardapio(@RequestBody CardapioProdutoAssociacaoEntradaDTO cardapioProdutoAssociacaoEntradaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardapioProdutoService.associarProdutoAoCardapio(cardapioProdutoAssociacaoEntradaDTO));
    }
}
