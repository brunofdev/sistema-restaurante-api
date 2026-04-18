package com.restaurante01.api_restaurante.modulos.produto.infraestrutura.adaptador;


import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.RepresentacaoProdutoItemPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoProdutoPorta;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso.ObterProdutoPorIdCasoDeUso;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoPedidoAdaptador implements PedidoProdutoPorta {

    private final ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso;

    public ProdutoPedidoAdaptador(ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso) {
        this.obterProdutoPorIdCasoDeUso = obterProdutoPorIdCasoDeUso;
    }

    @Override
    public RepresentacaoProdutoItemPedido obterProdutoVendido(Long id){
        Produto produto = obterProdutoPorIdCasoDeUso.retornarEntidade(id);
        return new RepresentacaoProdutoItemPedido(produto.getId(), produto.getNome());
    }
}
