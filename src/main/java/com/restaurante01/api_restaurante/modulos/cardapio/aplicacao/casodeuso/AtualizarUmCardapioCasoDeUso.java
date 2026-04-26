package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.CardapioDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.mapeador.CardapioMapeador;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.validador.CardapioValidador;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio.CardapioRepositorio;
import org.springframework.stereotype.Service;

@Service
public class AtualizarUmCardapioCasoDeUso {

    private final CardapioRepositorio cardapioRepositorio;
    private final CardapioMapeador cardapioMapeador;
    private final CardapioValidador cardapioValidador;
    private final BuscarCardapioPorIdCasoDeUso buscarCardapioPorId;

    public AtualizarUmCardapioCasoDeUso(CardapioRepositorio cardapioRepositorio,
                                        CardapioMapeador cardapioMapeador,
                                        CardapioValidador cardapioValidador,
                                        BuscarCardapioPorIdCasoDeUso buscarCardapioPorId) {
        this.cardapioRepositorio = cardapioRepositorio;
        this.cardapioMapeador = cardapioMapeador;
        this.cardapioValidador = cardapioValidador;
        this.buscarCardapioPorId = buscarCardapioPorId;
    }

    public CardapioDTO executar(CardapioDTO cardapioDTO) {
        Cardapio cardapioExistente = buscarCardapioPorId.executar(cardapioDTO.id());
        cardapioValidador.validarCardapio(cardapioDTO);
        Cardapio cardapioAtualizado = cardapioMapeador.atualizarCampos(cardapioExistente, cardapioDTO);
        cardapioRepositorio.save(cardapioAtualizado);
        return cardapioMapeador.mapearUmaEntidadeParaDTO(cardapioAtualizado);
    }
}
