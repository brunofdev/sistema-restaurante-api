package com.restaurante01.api_restaurante.cardapio.mapper;

import com.restaurante01.api_restaurante.cardapio.dto.CardapioDTO;
import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CardapioMapper {

    public CardapioDTO mappearUmCardapio(Cardapio cardapio){
        return new CardapioDTO(
                cardapio.getId(),
                cardapio.getNome(),
                cardapio.getDescricao(),
                cardapio.getDisponibilidade(),
                cardapio.getDataInicio(),
                cardapio.getDataFim());
    }
    public List<CardapioDTO>mappearLoteCardapio(List<Cardapio> loteCardapio ){
      return loteCardapio.stream()
              .map(this::mappearUmCardapio)
              .toList();
    }
}
