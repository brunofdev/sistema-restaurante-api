package com.restaurante01.api_restaurante.cardapio.controller;

import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.cardapio.service.CardapioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cardapio")
public class CardapioController {
    @Autowired
    private CardapioService cardapioService;

    @GetMapping("/listar-todos-cardapios")
    public ResponseEntity<List<Cardapio>> solicitarCardapios(){
        return ResponseEntity.ok(cardapioService.listarTodosCardapios());
    }

}
