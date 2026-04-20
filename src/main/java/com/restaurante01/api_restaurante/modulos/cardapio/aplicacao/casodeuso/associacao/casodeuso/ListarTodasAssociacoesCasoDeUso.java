package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.AssociacoesDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.mapeador.associacao.mapeador.CardapioProdutoMapeador;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio.CardapioProdutoRepositorio;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListarTodasAssociacoesCasoDeUso {

    private final CardapioProdutoRepositorio repository;
    private final CardapioProdutoMapeador mapper;

    public ListarTodasAssociacoesCasoDeUso(CardapioProdutoRepositorio repository, CardapioProdutoMapeador mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<AssociacoesDTO> executar() {
        return mapper.mapearCardapioComListaDeProduto(repository.findAll());
    }
}