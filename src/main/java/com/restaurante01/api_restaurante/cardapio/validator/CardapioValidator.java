package com.restaurante01.api_restaurante.cardapio.validator;

import com.restaurante01.api_restaurante.cardapio.dto.CardapioCreateDTO;
import com.restaurante01.api_restaurante.cardapio.exceptions.*;
import com.restaurante01.api_restaurante.cardapio.service.CardapioService;
import com.restaurante01.api_restaurante.core.utils.FormatarString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardapioValidator {

    public void validarCardapio(CardapioCreateDTO cardapio, boolean valor){
        if(valor == true){
            throw new CardapioMesmoNomeExcepetion("Já existe cardapio com o mesmo nome");
        }

        String nome = FormatarString.limparEspacos(cardapio.getNome());
        String descricao = FormatarString.limparEspacos(cardapio.getDescricao());

        if(nome.length() <= 3){
            throw new CardapioNomeInvalidoException("O nome deve possuir mais de três caracteres");
        }
        if(cardapio.getDataInicio().isAfter(cardapio.getDataFim())){
            throw new CardapioDataIniMaiorQueDataFimException("A data de inicio não pode ser maior que data fim");
        }
    }
}
