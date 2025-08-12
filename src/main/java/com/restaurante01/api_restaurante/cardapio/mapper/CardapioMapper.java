package com.restaurante01.api_restaurante.cardapio.mapper;

import com.restaurante01.api_restaurante.cardapio.dto.CardapioDTO;
import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.core.mapper.AbstractMapper;
import com.restaurante01.api_restaurante.core.mapper.Mapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CardapioMapper extends AbstractMapper<Cardapio, CardapioDTO> {

    @Override
    public CardapioDTO mapearEntityParaDTO(Cardapio cardapio){
        return new CardapioDTO(
                cardapio.getId(),
                cardapio.getNome(),
                cardapio.getDescricao(),
                cardapio.getDisponibilidade(),
                cardapio.getDataInicio(),
                cardapio.getDataFim());
    }
}
