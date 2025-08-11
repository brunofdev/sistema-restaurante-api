package com.restaurante01.api_restaurante.cardapio.service;

import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.cardapio.repository.CardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardapioService {
    private final CardapioRepository cardapioRepository;

    @Autowired
    public CardapioService(CardapioRepository cardapioRepository){
        this.cardapioRepository = cardapioRepository;
    }
    public List<Cardapio> listarTodosCardapios(){
        return cardapioRepository.findAll();
    }


}
