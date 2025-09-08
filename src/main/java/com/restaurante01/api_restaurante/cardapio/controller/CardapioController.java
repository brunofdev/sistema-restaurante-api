package com.restaurante01.api_restaurante.cardapio.controller;

import com.restaurante01.api_restaurante.cardapio.dto.CardapioCreateDTO;
import com.restaurante01.api_restaurante.cardapio.dto.CardapioDTO;
import com.restaurante01.api_restaurante.cardapio.service.CardapioService;
import com.restaurante01.api_restaurante.core.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cardapio")
public class CardapioController {
    @Autowired
    private CardapioService cardapioService;

    @GetMapping("/listar-todos")
    public ResponseEntity<ApiResponse<List<CardapioDTO>>> solicitarCardapios(){
        return ResponseEntity.ok(ApiResponse.success("Recurso encontrado" , cardapioService.listarTodosCardapios()));
    }
    @PostMapping("/adicionar-novo")
    public ResponseEntity<ApiResponse<CardapioDTO>> adicionarNovoCardapio(@Valid @RequestBody CardapioCreateDTO cardapioDTO){
        return ResponseEntity.ok(ApiResponse.success("Recurso criado" , cardapioService.adicionarNovoCardapio(cardapioDTO)));
    }
    /*ajustar
    @PutMapping("/atualizar-um/{cardapioId}")
    public ResponseEntity<ApiResponse<CardapioDTO>> atualizarCardapio(@PathVariable Long cardapioId, @Valid @RequestBody CardapioCreateDTO cardapioDTO){
        return ResponseEntity.ok(ApiResponse.success("Recurso atualizado", cardapioService.atualizarCardapio(cardapioId, cardapioDTO)));
    }
     */
    @DeleteMapping("{cardapioId}")
    public ResponseEntity<ApiResponse> deletarCardapio(@PathVariable("cardapioId") Long cardapioId){
            cardapioService.deletarCardapio(cardapioId);
            return ResponseEntity.ok(ApiResponse.success("Recurso removido", null));
        }
}
