package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.mapeador;

import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada.CriarCardapioDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.CardapioDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.compartilhado.mapper_padrao_abstract.AbstractMapper;
import com.restaurante01.api_restaurante.compartilhado.utils.formatadorstring.FormatarString;
import org.springframework.stereotype.Component;

@Component
public class CardapioMapeador extends AbstractMapper<Cardapio, CardapioDTO> {

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
    public Cardapio mapearCardapioCreateParaEntidade(CriarCardapioDTO criarCardapioDTO){
        Cardapio cardapio = new Cardapio();
        cardapio.setNome(FormatarString.limparEspacos(criarCardapioDTO.getNome()));
        cardapio.setDescricao(FormatarString.limparEspacos(criarCardapioDTO.getDescricao()));
        cardapio.setDisponibilidade(criarCardapioDTO.getDisponibilidade());
        cardapio.setDataInicio(criarCardapioDTO.getDataInicio());
        cardapio.setDataFim(criarCardapioDTO.getDataFim());
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

    public CardapioDTO mapearCardapioCreateParaCardapioDTO(CriarCardapioDTO criarCardapioDTO) {
        return new CardapioDTO(
                null,
                criarCardapioDTO.getNome(),
                criarCardapioDTO.getDescricao(),
                criarCardapioDTO.getDisponibilidade(),
                criarCardapioDTO.getDataInicio(),
                criarCardapioDTO.getDataFim()
        );
    }
}
