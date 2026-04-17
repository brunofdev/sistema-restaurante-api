package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.CardapioDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.mapeador.CardapioMapper;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.validador.CardapioValidator;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Cardapio;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio.CardapioRepositorio;
import org.springframework.stereotype.Service;

@Service
public class AtualizarUmCardapioCasoDeUso {

    private final CardapioRepositorio cardapioRepositorio;
    private final CardapioMapper cardapioMapper;
    private final CardapioValidator cardapioValidator;
    private final BuscarCardapioPorIdCasoDeUso buscarCardapioPorId;

    public AtualizarUmCardapioCasoDeUso(CardapioRepositorio cardapioRepositorio,
                                        CardapioMapper cardapioMapper,
                                        CardapioValidator cardapioValidator,
                                        BuscarCardapioPorIdCasoDeUso buscarCardapioPorId) {
        this.cardapioRepositorio = cardapioRepositorio;
        this.cardapioMapper = cardapioMapper;
        this.cardapioValidator = cardapioValidator;
        this.buscarCardapioPorId = buscarCardapioPorId;
    }

    public CardapioDTO executar(CardapioDTO cardapioDTO) {
        Cardapio cardapioExistente = buscarCardapioPorId.executar(cardapioDTO.getId());
        cardapioValidator.validarCardapio(cardapioDTO);
        Cardapio cardapioAtualizado = cardapioMapper.atualizarCampos(cardapioExistente, cardapioDTO);
        cardapioRepositorio.save(cardapioAtualizado);
        return cardapioMapper.mapearUmaEntidadeParaDTO(cardapioAtualizado);
    }
}
