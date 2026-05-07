package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.api.dto.saida.AssociacoesDTO;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.mapeador.associacao.mapeador.CardapioProdutoMapeador;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Associacao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.AssociacaoNaoExisteExcecao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio.CardapioProdutoRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarAssociacaoPorCardapioIdCasoDeUso {

    private final CardapioProdutoRepositorio repository;
    private final CardapioProdutoMapeador mapper;

    public ListarAssociacaoPorCardapioIdCasoDeUso(CardapioProdutoRepositorio repository, CardapioProdutoMapeador mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public AssociacoesDTO executar(long idCardapio) {
        List<Associacao> associacoes = repository.findByCardapioId(idCardapio);
        if (associacoes.isEmpty()) {
            throw new AssociacaoNaoExisteExcecao("Não existe associação para o cardápio informado");
        }
        return mapper.mapearCardapioComListaDeProduto(associacoes).get(0);
    }
}
