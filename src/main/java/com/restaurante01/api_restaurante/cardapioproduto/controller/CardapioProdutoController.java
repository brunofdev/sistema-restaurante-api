package com.restaurante01.api_restaurante.cardapioproduto.controller;


import com.restaurante01.api_restaurante.cardapioproduto.dto.entrada.CardapioProdutoAssociacaoEntradaDTO;
import com.restaurante01.api_restaurante.cardapioproduto.dto.saida.CardapioComListaProdutoDTO;
import com.restaurante01.api_restaurante.cardapioproduto.dto.saida.CardapioProdutoAssociacaoRespostaDTO;
import com.restaurante01.api_restaurante.cardapioproduto.dto.entrada.CardapioProdutoDTO;
import com.restaurante01.api_restaurante.cardapioproduto.service.CardapioProdutoService;
import com.restaurante01.api_restaurante.core.utils.ApiResponse;
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

    //Padronizar retorno de resposta utilizando a classe ApiResponse
    //Ajustar as exceptions para retornar o mesmo padrão das outras funcionalidades
    //Verificar metodo de delete
    @Autowired
    private CardapioProdutoService cardapioProdutoService;

    @GetMapping("/obter-todas-associacoes")
    public ResponseEntity<ApiResponse<List<CardapioComListaProdutoDTO>>> listarCardapioProdutos() {
        return ResponseEntity.ok(ApiResponse.success("Recurso disponbilizado", cardapioProdutoService.listarCardapiosProdutos()));
    }
    @GetMapping("/cardapio/{idCardapio}")
    public ResponseEntity<ApiResponse<CardapioProdutoDTO>> listarAssociacaoPorIdCardapio(@PathVariable long idCardapio){
        return ResponseEntity.ok(ApiResponse.success("Recurso encontrado", cardapioProdutoService.listarUmCardapioComProduto(idCardapio)));
    }
    @PostMapping("/associar-cardapioproduto")
    public ResponseEntity<ApiResponse<CardapioProdutoAssociacaoRespostaDTO>> associarProdutoCardapio(@RequestBody @Valid CardapioProdutoAssociacaoEntradaDTO cardapioProdutoAssociacaoEntradaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Recurso criado", cardapioProdutoService.criarAssociacaoProdutoCardapio(cardapioProdutoAssociacaoEntradaDTO)));
    }
    @DeleteMapping("cardapio{idCardapio}/produto{idProduto}")
    public ResponseEntity<ApiResponse<Void>> desassociarProdutoCardapio(@PathVariable @Min(1) long idCardapio, @PathVariable @Min(1) long idProduto){
            cardapioProdutoService.removerAssociacaoCardapioProduto(idCardapio, idProduto);
            return ResponseEntity.ok().body(ApiResponse.success("Recurso deletado", null));
    }
}
