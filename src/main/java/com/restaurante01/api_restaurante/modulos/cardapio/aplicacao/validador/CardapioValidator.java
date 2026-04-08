package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.validador;

import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.CardapioDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.*;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.CardapioDataIniMaiorQueDataFimException;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.CardapioMesmoNomeExcepetion;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.CardapioNomeInvalidoException;
import com.restaurante01.api_restaurante.modulos.cardapio.infraestrutura.persistencia.CardapioRepositorioJpa;
import com.restaurante01.api_restaurante.compartilhado.formatadores.FormatarString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardapioValidator {

    @Autowired
    private final CardapioRepositorioJpa cardapioRepositorioJpa;

    public CardapioValidator(CardapioRepositorioJpa cardapioRepositorioJpa) {
        this.cardapioRepositorioJpa = cardapioRepositorioJpa;
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
        Cardapio cardapioExistente = cardapioRepositorioJpa.findByNome(nomeCardapio);
        if (cardapioExistente != null) {
            if (cardapioDTO.getId() == null || !cardapioExistente.getId().equals(cardapioDTO.getId())) {
                throw new CardapioMesmoNomeExcepetion(
                    "Nome do Cardapio:  **" + nomeCardapio + "** já existe no sistema"
                );
            }
        }
    }
}

