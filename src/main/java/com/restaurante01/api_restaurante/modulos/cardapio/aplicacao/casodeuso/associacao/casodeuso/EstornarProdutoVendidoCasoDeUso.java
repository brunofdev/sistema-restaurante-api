package com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Associacao;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.repositorio.CardapioProdutoRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ItemPedidoClientePayload;
import com.restaurante01.api_restaurante.modulos.produto.dominio.excecao.ProdutoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
//Necessario implementar solução para realizar apenas uma consulta ao banco, nao em cada volta da iteração.
@Service
public class EstornarProdutoVendidoCasoDeUso {

    private final CardapioProdutoRepositorio repositorio;


    public EstornarProdutoVendidoCasoDeUso(CardapioProdutoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void executar(List<ItemPedidoClientePayload> itens, Long idCardapio){
        for (ItemPedidoClientePayload itemPedido : itens){
            Associacao produto = repositorio.findByCardapioIdAndProdutoId(idCardapio, itemPedido.idProduto())
                    .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto id: " + itemPedido.idProduto() + " não encontrado no cardapio com id " + idCardapio));
            produto.aumentarQuantidade(itemPedido.quantidade());
        }
    }
}
