package com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.repositorio;


import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;

import java.util.List;
import java.util.Optional;

public interface CardapioProdutoRepositorio {
    Optional<CardapioProduto> findByCardapioId(long id);
    Optional<CardapioProduto> findByCardapioIdAndProdutoId(long cardapioId, long produtoId);
    boolean existeAssociacao(long idCardapio, long idProduto);
    void deletarAssociacao(long idCardapio, long idProduto);
    CardapioProduto save(CardapioProduto cardapioProduto);
    List<CardapioProduto> findAll();
}
