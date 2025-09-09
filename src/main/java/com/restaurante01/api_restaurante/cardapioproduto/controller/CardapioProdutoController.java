package com.restaurante01.api_restaurante.cardapioproduto.controller;


import com.restaurante01.api_restaurante.cardapioproduto.dto.entrada.CardapioProdutoAssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.cardapioproduto.dto.saida.CardapioComListaProdutoDTO;
import com.restaurante01.api_restaurante.cardapioproduto.dto.saida.CardapioProdutoAssociacaoRespostaDTO;
import com.restaurante01.api_restaurante.cardapioproduto.dto.entrada.CardapioProdutoDTO;
import com.restaurante01.api_restaurante.cardapioproduto.service.CardapioProdutoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cardapioproduto")
@Validated
public class CardapioProdutoController {

    @Autowired
    private CardapioProdutoService cardapioProdutoService;

    @GetMapping("/obter-todas-associacoes")
    public ResponseEntity<List<CardapioComListaProdutoDTO>> listarCardapioProdutos() {
        return ResponseEntity.ok(cardapioProdutoService.listarCardapiosProdutos());
    }
    @GetMapping("/cardapio/{idCardapio}")
    public ResponseEntity<CardapioProdutoDTO> listarAssociacaoPorIdCardapio(@PathVariable long idCardapio){
        return ResponseEntity.ok(cardapioProdutoService.listarUmCardapioComProduto(idCardapio));
    }
    @PostMapping("/associar-cardapioproduto")
    public ResponseEntity<CardapioProdutoAssociacaoRespostaDTO> associarProdutoCardapio(@RequestBody @Valid CardapioProdutoAssociacaoEntradaDTO cardapioProdutoAssociacaoEntradaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardapioProdutoService.associarProdutoAoCardapio(cardapioProdutoAssociacaoEntradaDTO));
    }
    @DeleteMapping("cardapio{idCardapio}/produto{idProduto}")
    public ResponseEntity<Void> desassociarProdutoCardapio(@PathVariable @Min(1) long idCardapio, @PathVariable @Min(1) long idProduto){
        boolean removido = cardapioProdutoService.removerAssociacaoCardapioProduto(idCardapio, idProduto);
        if(removido){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
