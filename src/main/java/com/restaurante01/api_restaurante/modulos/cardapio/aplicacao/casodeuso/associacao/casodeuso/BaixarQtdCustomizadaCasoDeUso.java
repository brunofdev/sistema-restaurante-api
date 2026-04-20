package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Associacao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.excecao.AssociacaoNaoExisteExcecao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio.CardapioProdutoRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BaixarQtdCustomizadaCasoDeUso {

    private final CardapioProdutoRepositorio repository;

    public BaixarQtdCustomizadaCasoDeUso(CardapioProdutoRepositorio repository) {
        this.repository = repository;

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void executar(Long idCardapio, List<ItemPedido> itensPedido) {
        for (ItemPedido item : itensPedido) {
            Associacao produtoParaAtualizar = encontrarCardapioProduto(idCardapio, item.getProduto().idProduto());
            produtoParaAtualizar.diminuirQuantidade(item.getQuantidade());
            repository.save(produtoParaAtualizar);
        }
    }
    private Associacao encontrarCardapioProduto (Long idCardapio, Long idProduto){
        return repository.findByCardapioIdAndProdutoId(idCardapio, idProduto)
                .orElseThrow(() -> new AssociacaoNaoExisteExcecao("Não existe associação entre o cardápio e o produto enviado"));
    }
}