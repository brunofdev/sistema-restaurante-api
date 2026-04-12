package com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import com.restaurante01.api_restaurante.modulos.produto.infraestrutura.ProdutoRepositorioAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BaixarQuantidadeProdutoCasoDeUso {


    private final ProdutoRepositorioAdapter repositorio;
    private final ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso;

    public BaixarQuantidadeProdutoCasoDeUso(ProdutoRepositorioAdapter repositorio,
                                            ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso) {

        this.repositorio = repositorio;
        this.obterProdutoPorIdCasoDeUso = obterProdutoPorIdCasoDeUso;
    }

    //aqui seria interessante ter um bloco try, para caso não seja possivel atualizar por algum motivo, termos isso registrado
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void executar(List<ItemPedido> itensPedido) {
        for (ItemPedido item : itensPedido) {
            Produto produtoParaBaixarQuantidade = obterProdutoPorIdCasoDeUso.retornarEntidade(item.getProduto().getId());
            produtoParaBaixarQuantidade.diminuirQuantidade(item.getQuantidade());
            repositorio.save(produtoParaBaixarQuantidade);
        }
    }
}
