package com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.excecao.AssociacaoNaoExisteException;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.repositorio.CardapioProdutoRepositorio;
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
            CardapioProduto produtoParaAtualizar = encontrarCardapioProduto(idCardapio, item.getProduto().getId());
            produtoParaAtualizar.diminuirQuantidade(item.getQuantidade());
            repository.save(produtoParaAtualizar);
        }
    }
    private CardapioProduto encontrarCardapioProduto (Long idCardapio, Long idProduto){
        return repository.findByCardapioIdAndProdutoId(idCardapio, idProduto)
                .orElseThrow(() -> new AssociacaoNaoExisteException("Não existe associação entre o cardápio e o produto enviado"));
    }
}