package com.restaurante01.api_restaurante.cardapio.mapper;

import com.restaurante01.api_restaurante.cardapio.dto.CardapioDTO;
import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.core.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class CardapioMapper extends AbstractMapper<Cardapio, CardapioDTO> {

    @Override
    public CardapioDTO mapearUmaEntidadeParaDTO(Cardapio cardapio){
        return new CardapioDTO(
                cardapio.getId(),
                cardapio.getNome(),
                cardapio.getDescricao(),
                cardapio.getDisponibilidade(),
                cardapio.getDataInicio(),
                cardapio.getDataFim());
    }
    @Override
    public Cardapio mapearUmaDtoParaEntidade(CardapioDTO cardapioDTO){
        return new Cardapio(
                cardapioDTO.getId(),
                cardapioDTO.getNome(),
                cardapioDTO.getDescricao(),
                cardapioDTO.getDisponibilidade(),
                cardapioDTO.getDataInicio(),
                cardapioDTO.getDataFim()
                );
    }
    public Cardapio atualizarCampos(Cardapio cardapioExistente ,CardapioDTO cardapioAtualizado){
        cardapioExistente.setNome(cardapioAtualizado.getNome());
        cardapioExistente.setDescricao(cardapioAtualizado.getDescricao());
        cardapioExistente.setDisponibilidade(cardapioAtualizado.getDisponibilidade());
        cardapioExistente.setDataInicio(cardapioAtualizado.getDataInicio());
        cardapioExistente.setDataFim(cardapioAtualizado.getDataFim());
        return cardapioExistente;
    }
}
