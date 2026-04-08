package com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.api.dto.saida.CardapioComListaProdutoDTO;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.mapeador.CardapioProdutoMapper;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.repositorio.CardapioProdutoRepositorio;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListarTodasAssociacoesCasoDeUso {

    private final CardapioProdutoRepositorio repository;
    private final CardapioProdutoMapper mapper;

    public ListarTodasAssociacoesCasoDeUso(CardapioProdutoRepositorio repository, CardapioProdutoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CardapioComListaProdutoDTO> executar() {
        return mapper.mapearCardapioComListaDeProduto(repository.findAll());
    }
}