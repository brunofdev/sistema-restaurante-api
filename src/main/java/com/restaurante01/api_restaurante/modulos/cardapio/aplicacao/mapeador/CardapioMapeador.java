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
                cardapioDTO.id(),
                FormatarString.limparEspacos(cardapioDTO.nome()),
                FormatarString.limparEspacos(cardapioDTO.descricao()),
                cardapioDTO.disponibilidade(),
                cardapioDTO.dataInicio(),
                cardapioDTO.dataFim()
                );
    }
    public Cardapio mapearCardapioCreateParaEntidade(CriarCardapioDTO criarCardapioDTO){
        Cardapio cardapio = new Cardapio();
        cardapio.setNome(FormatarString.limparEspacos(criarCardapioDTO.nome()));
        cardapio.setDescricao(FormatarString.limparEspacos(criarCardapioDTO.descricao()));
        cardapio.setDisponibilidade(criarCardapioDTO.disponibilidade());
        cardapio.setDataInicio(criarCardapioDTO.dataInicio());
        cardapio.setDataFim(criarCardapioDTO.dataFim());
        return cardapio;
    }
    public Cardapio atualizarCampos(Cardapio cardapioExistente , CardapioDTO cardapioAtualizado){
        cardapioExistente.setNome(FormatarString.limparEspacos(cardapioAtualizado.nome()));
        cardapioExistente.setDescricao(FormatarString.limparEspacos(cardapioAtualizado.descricao()));
        cardapioExistente.setDisponibilidade(cardapioAtualizado.disponibilidade());
        cardapioExistente.setDataInicio(cardapioAtualizado.dataInicio());
        cardapioExistente.setDataFim(cardapioAtualizado.dataFim());
        return cardapioExistente;
    }

    public CardapioDTO mapearCardapioCreateParaCardapioDTO(CriarCardapioDTO criarCardapioDTO) {
        return new CardapioDTO(
                null,
                criarCardapioDTO.nome(),
                criarCardapioDTO.descricao(),
                criarCardapioDTO.disponibilidade(),
                criarCardapioDTO.dataInicio(),
                criarCardapioDTO.dataFim()
        );
    }
}
