package com.restaurante01.api_restaurante.cardapio.validator;

import com.restaurante01.api_restaurante.cardapio.dto.CardapioCreateDTO;
import com.restaurante01.api_restaurante.cardapio.dto.CardapioDTO;
import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.cardapio.exceptions.*;
import com.restaurante01.api_restaurante.cardapio.repository.CardapioRepository;
import com.restaurante01.api_restaurante.cardapio.service.CardapioService;
import com.restaurante01.api_restaurante.core.utils.FormatarString;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import com.restaurante01.api_restaurante.produto.exceptions.ProdutoMesmoNomeExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class CardapioValidator {

    @Autowired
    private final CardapioRepository cardapioRepository;

    public CardapioValidator(CardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }


    public void validarCardapio(CardapioDTO cardapio, boolean valor) {
        String nomeCardapio = FormatarString.limparEspacos(cardapio.getNome());
        Cardapio cardapioExistente = cardapioRepository.findByNome(nomeCardapio);
        if (cardapioExistente != null) {
            if (cardapio.getId() == null || !cardapioExistente.getId().equals(cardapio.getId())) {
                throw new ProdutoMesmoNomeExistenteException(
                        "Nome de Produto  **" + nomeCardapio + "** já existe no sistema"
                );
            }
                if (valor == true) {
                    throw new CardapioMesmoNomeExcepetion("Já existe cardapio com o mesmo nome");
                }
                String nome = FormatarString.limparEspacos(cardapio.getNome());
                String descricao = FormatarString.limparEspacos(cardapio.getDescricao());

                if (nome.length() <= 3) {
                    throw new CardapioNomeInvalidoException("O nome deve possuir mais de três caracteres");
                }
                if (cardapio.getDataInicio().isAfter(cardapio.getDataFim())) {
                    throw new CardapioDataIniMaiorQueDataFimException("A data de inicio não pode ser maior que data fim");
                }
            }
        }
    }

