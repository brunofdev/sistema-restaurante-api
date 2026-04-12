package com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.repositorio.CardapioProdutoRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.ProdutoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstornarProdutoVendidoCasoDeUso {

    private final CardapioProdutoRepositorio repositorio;


    public EstornarProdutoVendidoCasoDeUso(CardapioProdutoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void executar(List<ItemPedido> itens, Long idCardapio){
        for (ItemPedido itemPedido : itens){
            CardapioProduto produto = repositorio.findByCardapioIdAndProdutoId(idCardapio, itemPedido.getProduto().getId())
                    .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto id: " + itemPedido.getProduto().getId() + " não encontrado no cardapio com id " + idCardapio));
            produto.aumentarQuantidade(itemPedido.getQuantidade());
        }
    }
}
