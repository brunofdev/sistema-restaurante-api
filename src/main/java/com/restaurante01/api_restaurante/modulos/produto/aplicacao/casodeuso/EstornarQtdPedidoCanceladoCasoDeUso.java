package com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ItemPedidoPayload;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import com.restaurante01.api_restaurante.modulos.produto.dominio.repositorio.ProdutoRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstornarQtdPedidoCanceladoCasoDeUso {
    private final ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso;
    private final ProdutoRepositorio repositorio;

    public EstornarQtdPedidoCanceladoCasoDeUso(ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso, ProdutoRepositorio repositorio) {
        this.obterProdutoPorIdCasoDeUso = obterProdutoPorIdCasoDeUso;
        this.repositorio = repositorio;
    }
    //Precisa melhorar para fazer apenas uma consulta no banco
    public void executar (List<ItemPedidoPayload> itens){
        for(ItemPedidoPayload item : itens){
            Produto produto = obterProdutoPorIdCasoDeUso.retornarEntidade(item.idProduto());
            produto.aumentarQuantidade(item.quantidade());
            repositorio.save(produto);
        }
    }
}
