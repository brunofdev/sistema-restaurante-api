package com.restaurante01.api_restaurante.cardapioproduto.controller;


import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioComListaProdutoDTO;
import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioProdutoAssociacaoDTO;
import com.restaurante01.api_restaurante.cardapioproduto.dto.CardapioProdutoDTO;
import com.restaurante01.api_restaurante.cardapioproduto.entity.CardapioProduto;
import com.restaurante01.api_restaurante.cardapioproduto.service.CardapioProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<CardapioProdutoAssociacaoDTO> associarProdutoCardapio(@RequestParam Long idProduto,
                                                                                @RequestParam Long idCardapio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardapioProdutoService.associarProdutoAoCardapio(idProduto, idCardapio));
    }
    @DeleteMapping("cardapio{idCardapio}/produto{idProduto}")
    public ResponseEntity<Void> desassociarProdutoCardapio(@PathVariable long idCardapio, @PathVariable long idProduto){
        boolean removido = cardapioProdutoService.desassociarProdutoCardapio(idCardapio, idProduto);
        if(removido){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
