package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada.CardapioCreateDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.CardapioDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.mapeamento.CardapioMapper;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.validador.CardapioValidator;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio.CardapioRepositorio;
import org.springframework.stereotype.Service;

@Service
public class CriarCardapioCasoDeUso {

    private final CardapioRepositorio cardapioRepositorio;
    private final CardapioMapper cardapioMapper;
    private final CardapioValidator cardapioValidator;

    public CriarCardapioCasoDeUso(CardapioRepositorio cardapioRepositorio, CardapioMapper cardapioMapper, CardapioValidator cardapioValidator) {
        this.cardapioRepositorio = cardapioRepositorio;
        this.cardapioMapper = cardapioMapper;
        this.cardapioValidator = cardapioValidator;
    }
    public CardapioDTO executar (CardapioCreateDTO dto){
        CardapioDTO cardapioDTO = cardapioMapper.mapearCardapioCreateParaCardapioDTO(dto);
        cardapioValidator.validarCardapio(cardapioDTO);
        Cardapio novoCardapio = cardapioMapper.mapearCardapioCreateParaEntidade(dto);
        cardapioRepositorio.save(novoCardapio);
        return cardapioMapper.mapearUmaEntidadeParaDTO(novoCardapio);
    }
}

