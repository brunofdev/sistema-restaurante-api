package com.restaurante01.api_restaurante.cardapio.service;

import com.restaurante01.api_restaurante.cardapio.dto.CardapioDTO;
import com.restaurante01.api_restaurante.cardapio.mapper.CardapioMapper;
import com.restaurante01.api_restaurante.cardapio.repository.CardapioRepository;
import com.restaurante01.api_restaurante.cardapio.validator.CardapioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CardapioService {
    private final CardapioRepository cardapioRepository;
    private final CardapioMapper cardapioMapper;
    private final CardapioValidator cardapioValidator;

    @Autowired
    public CardapioService(CardapioRepository cardapioRepository, CardapioMapper cardapioMapper, CardapioValidator cardapioValidator){
        this.cardapioRepository = cardapioRepository;
        this.cardapioMapper = cardapioMapper;
        this.cardapioValidator = cardapioValidator;
    }
    public List<CardapioDTO> listarTodosCardapios(){
        return cardapioMapper.mapearListaDeEntidadeParaDTO(cardapioRepository.findAll());
    }
}
