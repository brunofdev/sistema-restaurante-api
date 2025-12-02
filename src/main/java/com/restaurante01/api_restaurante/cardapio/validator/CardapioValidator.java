package com.restaurante01.api_restaurante.cardapio.validator;

import com.restaurante01.api_restaurante.cardapio.dto.saida.CardapioDTO;
import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.cardapio.exceptions.*;
import com.restaurante01.api_restaurante.cardapio.repository.CardapioRepository;
import com.restaurante01.api_restaurante.core.utils.formatadores.FormatarString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardapioValidator {

    @Autowired
    private final CardapioRepository cardapioRepository;

    public CardapioValidator(CardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }

    public void validarCardapio(CardapioDTO cardapioDTO) {
        String nomeCardapio = FormatarString.limparEspacos(cardapioDTO.getNome());
        String descricao = FormatarString.limparEspacos(cardapioDTO.getDescricao());
        if (nomeCardapio.length() <= 3) {
            throw new CardapioNomeInvalidoException("O nome deve possuir mais de três caracteres");
        }
        if (cardapioDTO.getDataInicio().isAfter(cardapioDTO.getDataFim())) {
            throw new CardapioDataIniMaiorQueDataFimException("A data de inicio não pode ser maior que data fim");
        }
        //garante idempotência no verbo put do controlador
        Cardapio cardapioExistente = cardapioRepository.findByNome(nomeCardapio);
        if (cardapioExistente != null) {
            if (cardapioDTO.getId() == null || !cardapioExistente.getId().equals(cardapioDTO.getId())) {
                throw new CardapioMesmoNomeExcepetion(
                    "Nome do Cardapio:  **" + nomeCardapio + "** já existe no sistema"
                );
            }
        }
    }
}

