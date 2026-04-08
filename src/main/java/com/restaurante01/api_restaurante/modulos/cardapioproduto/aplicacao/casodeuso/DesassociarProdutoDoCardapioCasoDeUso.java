package com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.excecao.AssociacaoNaoExisteException;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.repositorio.CardapioProdutoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DesassociarProdutoDoCardapioCasoDeUso {

    private final CardapioProdutoRepositorio repository;

    public DesassociarProdutoDoCardapioCasoDeUso(CardapioProdutoRepositorio repository) {
        this.repository = repository;
    }

    @Transactional
    public void executar(long idCardapio, long idProduto) {
        if (!repository.existeAssociacao(idCardapio, idProduto)) {
            throw new AssociacaoNaoExisteException("Não existe associação entre produto e cardápio para remoção");
        }
        repository.deletarAssociacao(idCardapio, idProduto);
    }
}