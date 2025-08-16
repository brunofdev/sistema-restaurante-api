package com.restaurante01.api_restaurante.cardapio.validator;

import com.restaurante01.api_restaurante.cardapio.dto.CardapioDTO;
import com.restaurante01.api_restaurante.cardapio.exceptions.*;

public class CardapioValidator {

    public void validarCardapio(CardapioDTO cardapio){
        if(cardapio.getId() == null){
            throw new CardapioIdVazioException("Id não pode ser vazio");
        }
        else if(cardapio.getNome() == null || cardapio.getNome().isEmpty()){
            throw new CardapioSemNomeException("O nome não pode ser vazio");
        }
        else if(cardapio.getDescricao() == null || cardapio.getDescricao().isEmpty()){
            throw new CardapioSemDescException("O campo descrição não pode ser vazio");
        }
        else if(cardapio.getDataInicio().isAfter(cardapio.getDataFim())){
            throw new CardapioDataIniMaiorQueDataFimException("A data de inicio não pode ser maior que data fim");
        }
        else if (cardapio.getDisponibilidade() == null){
            throw new CardapioDisponibilidadeVazioException("A disponibilidade do cardapio deve ser preenchida");
        }
    }

}
