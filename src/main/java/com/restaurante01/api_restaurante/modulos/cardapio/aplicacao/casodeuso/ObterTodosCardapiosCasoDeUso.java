package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.CardapioDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.mapeador.CardapioMapeador;
import com.restaurante01.api_restaurante.modulos.cardapio.infraestrutura.adaptador.CardapioJpaAdaptador;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObterTodosCardapiosCasoDeUso {

    private final CardapioMapeador mapper;
    private final CardapioJpaAdaptador repositorio;

    public ObterTodosCardapiosCasoDeUso(CardapioMapeador mapper, CardapioJpaAdaptador repositorio) {
        this.mapper = mapper;
        this.repositorio = repositorio;
    }

    public List<CardapioDTO> executar (){
        return mapper.mapearListaDeEntidadeParaDTO(repositorio.findAll());
    }
}
