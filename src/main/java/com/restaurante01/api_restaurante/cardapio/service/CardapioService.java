package com.restaurante01.api_restaurante.cardapio.service;

import com.restaurante01.api_restaurante.cardapio.dto.CardapioDTO;
import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.cardapio.mapper.CardapioMapper;
import com.restaurante01.api_restaurante.cardapio.repository.CardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardapioService {
    private final CardapioRepository cardapioRepository;
    private final CardapioMapper cardapioMapper;

    @Autowired
    public CardapioService(CardapioRepository cardapioRepository, CardapioMapper cardapioMapper){
        this.cardapioRepository = cardapioRepository;
        this.cardapioMapper = cardapioMapper;
    }
    public List<CardapioDTO> listarTodosCardapios(){
        return cardapioRepository.findAll();
    }


}
