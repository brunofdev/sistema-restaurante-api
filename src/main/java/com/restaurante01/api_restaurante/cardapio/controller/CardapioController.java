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

    @GetMapping("/listar-todos-cardapios")
    public ResponseEntity<List<CardapioDTO>> solicitarCardapios(){
        return ResponseEntity.ok(cardapioService.listarTodosCardapios());

    }
    @PostMapping("/adicionar-novo-cardapio")
    public ResponseEntity<CardapioDTO> adicionarNovoCardapio(@Valid @RequestBody CardapioCreateDTO cardapioDTO){
        return ResponseEntity.ok(cardapioService.adicionarNovoCardapio(cardapioDTO));
    }
    @PutMapping("/atualizar-um-cardapio/{id}")
    public ResponseEntity<CardapioDTO> atualizarCardapio(@PathVariable Long id, @Valid @RequestBody CardapioCreateDTO cardapioDTO){
        return ResponseEntity.ok(cardapioService.atualizarCardapio(id, cardapioDTO));
    }
    @DeleteMapping("deletar-por-id/{id}")
        public ResponseEntity<CardapioDTO> deletarCardapio(@PathVariable Long id){
            cardapioService.deletarCardapio(id);
            return ResponseEntity.noContent().build();
        }

}
