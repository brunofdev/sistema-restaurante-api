package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.validador;

import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.CardapioDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.CardapioDataIniMaiorQueDataFimExcecao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.CardapioMesmoNomeExcecao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.CardapioNomeInvalidoExcecao;
import com.restaurante01.api_restaurante.modulos.cardapio.infraestrutura.persistencia.CardapioJPA;
import com.restaurante01.api_restaurante.compartilhado.utils.formatadorstring.FormatarString;
import org.springframework.stereotype.Component;

@Component
public class CardapioValidador {

    private final CardapioJPA cardapioJpa;

    public CardapioValidador(CardapioJPA cardapioJpa) {
        this.cardapioJpa = cardapioJpa;
    }

    public void validarCardapio(CardapioDTO cardapioDTO) {
        String nomeCardapio = FormatarString.limparEspacos(cardapioDTO.nome());
        String descricao = FormatarString.limparEspacos(cardapioDTO.descricao());
        if (nomeCardapio.length() <= 3) {
            throw new CardapioNomeInvalidoExcecao("O nome deve possuir mais de três caracteres");
        }
        if (cardapioDTO.dataInicio().isAfter(cardapioDTO.dataFim())) {
            throw new CardapioDataIniMaiorQueDataFimExcecao("A data de inicio não pode ser maior que data fim");
        }
        //garante idempotência no verbo put do controlador
        Cardapio cardapioExistente = cardapioJpa.findByNome(nomeCardapio);
        if (cardapioExistente != null) {
            if (cardapioDTO.id() == null || !cardapioExistente.getId().equals(cardapioDTO.id())) {
                throw new CardapioMesmoNomeExcecao(
                    "Nome do Cardapio:  **" + nomeCardapio + "** já existe no sistema"
                );
            }
        }
    }
}

