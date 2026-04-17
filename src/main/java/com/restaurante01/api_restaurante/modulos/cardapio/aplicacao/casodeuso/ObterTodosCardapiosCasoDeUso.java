package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.CardapioDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.mapeador.CardapioMapper;
import com.restaurante01.api_restaurante.modulos.cardapio.infraestrutura.persistencia.CardapioRepositorioAdaptador;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObterTodosCardapiosCasoDeUso {

    private final CardapioMapper mapper;
    private final CardapioRepositorioAdaptador repositorio;

    public ObterTodosCardapiosCasoDeUso(CardapioMapper mapper, CardapioRepositorioAdaptador repositorio) {
        this.mapper = mapper;
        this.repositorio = repositorio;
    }

    public List<CardapioDTO> executar (){
        return mapper.mapearListaDeEntidadeParaDTO(repositorio.findAll());
    }
}
