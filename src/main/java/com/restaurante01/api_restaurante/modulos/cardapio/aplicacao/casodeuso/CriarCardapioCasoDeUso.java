package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada.CriarCardapioDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.CardapioDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.mapeador.CardapioMapeador;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.validador.CardapioValidador;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio.CardapioRepositorio;
import org.springframework.stereotype.Service;

@Service
public class CriarCardapioCasoDeUso {

    private final CardapioRepositorio cardapioRepositorio;
    private final CardapioMapeador cardapioMapeador;
    private final CardapioValidador cardapioValidador;

    public CriarCardapioCasoDeUso(CardapioRepositorio cardapioRepositorio, CardapioMapeador cardapioMapeador, CardapioValidador cardapioValidador) {
        this.cardapioRepositorio = cardapioRepositorio;
        this.cardapioMapeador = cardapioMapeador;
        this.cardapioValidador = cardapioValidador;
    }
    public CardapioDTO executar (CriarCardapioDTO dto){
        CardapioDTO cardapioDTO = cardapioMapeador.mapearCardapioCreateParaCardapioDTO(dto);
        cardapioValidador.validarCardapio(cardapioDTO);
        Cardapio novoCardapio = cardapioMapeador.mapearCardapioCreateParaEntidade(dto);
        cardapioRepositorio.save(novoCardapio);
        return cardapioMapeador.mapearUmaEntidadeParaDTO(novoCardapio);
    }
}

