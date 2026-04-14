package com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import com.restaurante01.api_restaurante.modulos.produto.dominio.repositorio.ProdutoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void executar (List<ItemPedido> itens){
        for(ItemPedido item : itens){
            Produto produto = obterProdutoPorIdCasoDeUso.retornarEntidade(item.getProduto().getId());
            produto.aumentarQuantidade(item.getQuantidade());
            repositorio.save(produto);
        }
    }
}
