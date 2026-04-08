package com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.api.dto.entrada.CardapioProdutoDTO;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.mapeador.CardapioProdutoMapper;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.excecao.AssociacaoNaoExisteException;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.repositorio.CardapioProdutoRepositorio;
import org.springframework.stereotype.Service;

@Service
public class ListarAssociacaoPorCardapioIdCasoDeUso {

    private final CardapioProdutoRepositorio repository;
    private final CardapioProdutoMapper mapper;

    public ListarAssociacaoPorCardapioIdCasoDeUso(CardapioProdutoRepositorio repository, CardapioProdutoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CardapioProdutoDTO executar(long idCardapio) {
        return repository.findByCardapioId(idCardapio)
                .map(mapper::mapearUmaEntidadeParaDTO)
                .orElseThrow(() -> new AssociacaoNaoExisteException("Não existe associação para o cardápio informado"));
    }
}