package com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio;


import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Associacao;

import java.util.List;
import java.util.Optional;

public interface CardapioProdutoRepositorio {
    Optional<Associacao> findByCardapioId(long id);
    Optional<Associacao> findByCardapioIdAndProdutoId(long cardapioId, long produtoId);
    boolean existeAssociacao(long idCardapio, long idProduto);
    void deletarAssociacao(long idCardapio, long idProduto);
    Associacao save(Associacao associacao);
    List<Associacao> findAll();
    List<Associacao> buscarItensDoPedido(Long idCardapio, List<Long> idsProdutos);
}

