package com.restaurante01.api_restaurante.cardapio.validator;

import com.restaurante01.api_restaurante.cardapio.dto.CardapioDTO;
import com.restaurante01.api_restaurante.cardapio.exceptions.*;
import org.springframework.stereotype.Component;

@Component
public class CardapioValidator {

    public void validarCardapio(CardapioDTO cardapio){
        if(cardapio.getNome() == null || cardapio.getNome().isEmpty()){
            throw new CardapioSemNomeException("O nome não pode ser vazio");
        }
        if(cardapio.getDescricao() == null || cardapio.getDescricao().isEmpty()){
            throw new CardapioSemDescException("O campo descrição não pode ser vazio");
        }
        if(cardapio.getDataInicio().isAfter(cardapio.getDataFim())){
            throw new CardapioDataIniMaiorQueDataFimException("A data de inicio não pode ser maior que data fim");
        }
        if (cardapio.getDisponibilidade() == null){
            throw new CardapioDisponibilidadeVazioException("A disponibilidade do cardapio deve ser preenchida");
        }
    }

}
