package com.restaurante01.api_restaurante.modulos.cardapio.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Associacao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio.CardapioProdutoRepositorio;
import com.restaurante01.api_restaurante.modulos.cardapio.infraestrutura.persistencia.AssociacaoJPA;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AssociacaoJpaAdaptador implements CardapioProdutoRepositorio {
    private final AssociacaoJPA jpa;

    public AssociacaoJpaAdaptador(AssociacaoJPA jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<Associacao> findByCardapioId(long id) {
        return jpa.findByCardapioId(id);
    }

    @Override
    public Optional<Associacao> findByCardapioIdAndProdutoId(long cardapioId, long produtoId) {
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
    public Associacao save(Associacao associacao) {
        return jpa.save(associacao);
    }

    @Override
    public List<Associacao> findAll() {
        return jpa.findAll();
    }

    @Override
    public List<Associacao> buscarItensDoPedido(Long idCardapio, List<Long> idsProdutos){
        return jpa.buscarItensDoPedido(idCardapio, idsProdutos);
    }
}