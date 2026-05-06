package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Associacao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio.CardapioProdutoRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ItemPedidoPayload;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.ProdutoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//Necessario implementar solução para realizar apenas uma consulta ao banco, nao em cada volta da iteração.
@Service
public class EstornarProdutoVendidoCasoDeUso {

    private final CardapioProdutoRepositorio repositorio;


    public EstornarProdutoVendidoCasoDeUso(CardapioProdutoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void executar(List<ItemPedidoPayload> itens, Long idCardapio){
        for (ItemPedidoPayload itemPedido : itens){
            Associacao produto = repositorio.findByCardapioIdAndProdutoId(idCardapio, itemPedido.idProduto())
                    .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto id: " + itemPedido.idProduto() + " não encontrado no cardapio com id " + idCardapio));
            produto.aumentarQuantidade(itemPedido.quantidade());
        }
    }
}
