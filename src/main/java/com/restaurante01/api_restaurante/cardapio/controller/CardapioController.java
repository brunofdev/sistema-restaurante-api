package com.restaurante01.api_restaurante.cardapio.controller;

import com.restaurante01.api_restaurante.cardapio.dto.CardapioCreateDTO;
import com.restaurante01.api_restaurante.cardapio.dto.CardapioDTO;
import com.restaurante01.api_restaurante.cardapio.service.CardapioService;
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
    public ResponseEntity<List<CardapioDTO>> solicitarCardapios(){
        return ResponseEntity.ok(cardapioService.listarTodosCardapios());
    }
    @PostMapping("/adicionar-novo")
    public ResponseEntity<CardapioDTO> adicionarNovoCardapio(@Valid @RequestBody CardapioCreateDTO cardapioDTO){
        return ResponseEntity.ok(cardapioService.adicionarNovoCardapio(cardapioDTO));
    }
    @PutMapping("/atualizar-um/{cardapioId}")
    public ResponseEntity<CardapioDTO> atualizarCardapio(@PathVariable Long cardapioId, @Valid @RequestBody CardapioCreateDTO cardapioDTO){
        return ResponseEntity.ok(cardapioService.atualizarCardapio(cardapioId, cardapioDTO));
    }
    @DeleteMapping("{cardapioId}")
        public ResponseEntity<Void> deletarCardapio(@PathVariable("cardapioId") Long cardapioId){
            cardapioService.deletarCardapio(cardapioId);
            return ResponseEntity.noContent().build();
        }
}
