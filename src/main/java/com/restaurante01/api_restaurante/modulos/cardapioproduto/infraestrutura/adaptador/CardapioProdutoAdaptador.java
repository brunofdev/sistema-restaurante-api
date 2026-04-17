package com.restaurante01.api_restaurante.modulos.cardapioproduto.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.repositorio.CardapioProdutoRepositorio;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.infraestrutura.persistencia.CardapioProdutoJPA;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CardapioProdutoAdaptador implements CardapioProdutoRepositorio {
    private final CardapioProdutoJPA jpa;

    public CardapioProdutoAdaptador(CardapioProdutoJPA jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<CardapioProduto> findByCardapioId(long id) {
        return jpa.findByCardapioId(id);
    }

    @Override
    public Optional<CardapioProduto> findByCardapioIdAndProdutoId(long cardapioId, long produtoId) {
        return jpa.findByCardapioIdAndProdutoId(cardapioId, produtoId);
    }

    @Override
    public boolean existeAssociacao(long idCardapio, long idProduto) {
        return jpa.encontrarProdutoCardapio(idCardapio, idProduto);
    }

    @Override
    @Transactional
    public void deletarAssociacao(long idCardapio, long idProduto) {
        jpa.deleteProdutoFromCardapio(idCardapio, idProduto);
    }

    @Override
    public CardapioProduto save(CardapioProduto cardapioProduto) {
        return jpa.save(cardapioProduto);
    }

    @Override
    public List<CardapioProduto> findAll() {
        return jpa.findAll();
    }

    @Override
    public List<CardapioProduto> buscarItensDoPedido(Long idCardapio, List<Long> idsProdutos){
        return jpa.buscarItensDoPedido(idCardapio, idsProdutos);
    }
}