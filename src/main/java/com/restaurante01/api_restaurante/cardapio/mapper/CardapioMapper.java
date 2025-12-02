package com.restaurante01.api_restaurante.cardapio.mapper;

import com.restaurante01.api_restaurante.cardapio.dto.entrada.CardapioCreateDTO;
import com.restaurante01.api_restaurante.cardapio.dto.saida.CardapioDTO;
import com.restaurante01.api_restaurante.cardapio.entity.Cardapio;
import com.restaurante01.api_restaurante.core.utils.mapper_padrao_abstract.AbstractMapper;
import com.restaurante01.api_restaurante.core.utils.formatadores.FormatarString;
import org.springframework.stereotype.Component;

@Component
public class CardapioMapper extends AbstractMapper<Cardapio, CardapioDTO> {

    @Override
    public CardapioDTO mapearUmaEntidadeParaDTO(Cardapio cardapio){
        return new CardapioDTO(
                cardapio.getId(),
                FormatarString.limparEspacos(cardapio.getNome()),
                FormatarString.limparEspacos(cardapio.getDescricao()),
                cardapio.getDisponibilidade(),
                cardapio.getDataInicio(),
                cardapio.getDataFim());
    }
    @Override
    public Cardapio mapearUmaDtoParaEntidade(CardapioDTO cardapioDTO){
        return new Cardapio(
                cardapioDTO.getId(),
                FormatarString.limparEspacos(cardapioDTO.getNome()),
                FormatarString.limparEspacos(cardapioDTO.getDescricao()),
                cardapioDTO.getDisponibilidade(),
                cardapioDTO.getDataInicio(),
                cardapioDTO.getDataFim()
                );
    }
    public Cardapio mapearCardapioCreateParaEntidade(CardapioCreateDTO cardapioCreateDTO){
        Cardapio cardapio = new Cardapio();
        cardapio.setNome(FormatarString.limparEspacos(cardapioCreateDTO.getNome()));
        cardapio.setDescricao(FormatarString.limparEspacos(cardapioCreateDTO.getDescricao()));
        cardapio.setDisponibilidade(cardapioCreateDTO.getDisponibilidade());
        cardapio.setDataInicio(cardapioCreateDTO.getDataInicio());
        cardapio.setDataFim(cardapioCreateDTO.getDataFim());
        return cardapio;
    }
    public Cardapio atualizarCampos(Cardapio cardapioExistente , CardapioDTO cardapioAtualizado){
        cardapioExistente.setNome(FormatarString.limparEspacos(cardapioAtualizado.getNome()));
        cardapioExistente.setDescricao(FormatarString.limparEspacos(cardapioAtualizado.getDescricao()));
        cardapioExistente.setDisponibilidade(cardapioAtualizado.getDisponibilidade());
        cardapioExistente.setDataInicio(cardapioAtualizado.getDataInicio());
        cardapioExistente.setDataFim(cardapioAtualizado.getDataFim());
        return cardapioExistente;
    }

    public CardapioDTO mapearCardapioCreateParaCardapioDTO(CardapioCreateDTO cardapioCreateDTO) {
        return new CardapioDTO(
                null,
                cardapioCreateDTO.getNome(),
                cardapioCreateDTO.getDescricao(),
                cardapioCreateDTO.getDisponibilidade(),
                cardapioCreateDTO.getDataInicio(),
                cardapioCreateDTO.getDataFim()
        );
    }
}
