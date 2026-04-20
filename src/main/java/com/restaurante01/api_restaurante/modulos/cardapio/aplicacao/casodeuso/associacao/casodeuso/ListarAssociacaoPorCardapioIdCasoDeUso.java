package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.entrada.AssociacaoDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.mapeador.associacao.mapeador.CardapioProdutoMapeador;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.AssociacaoNaoExisteExcecao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio.CardapioProdutoRepositorio;
import org.springframework.stereotype.Service;

@Service
public class ListarAssociacaoPorCardapioIdCasoDeUso {

    private final CardapioProdutoRepositorio repository;
    private final CardapioProdutoMapeador mapper;

    public ListarAssociacaoPorCardapioIdCasoDeUso(CardapioProdutoRepositorio repository, CardapioProdutoMapeador mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public AssociacaoDTO executar(long idCardapio) {
        return repository.findByCardapioId(idCardapio)
                .map(mapper::mapearUmaEntidadeParaDTO)
                .orElseThrow(() -> new AssociacaoNaoExisteExcecao("Não existe associação para o cardápio informado"));
    }
}