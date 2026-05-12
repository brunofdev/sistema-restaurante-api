package com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ItemPedidoClientePayload;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import com.restaurante01.api_restaurante.modulos.produto.infraestrutura.adaptador.ProdutoJpaAdaptador;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaixarQuantidadeProdutoCasoDeUso {


    private final ProdutoJpaAdaptador repositorio;
    private final ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso;

    public BaixarQuantidadeProdutoCasoDeUso(ProdutoJpaAdaptador repositorio,
                                            ObterProdutoPorIdCasoDeUso obterProdutoPorIdCasoDeUso) {

        this.repositorio = repositorio;
        this.obterProdutoPorIdCasoDeUso = obterProdutoPorIdCasoDeUso;
    }

    //aqui seria interessante ter um bloco try, para caso não seja possivel atualizar por algum motivo, termos isso registrado
    public void executar(List<ItemPedidoClientePayload> itensPedido) {
        for (ItemPedidoClientePayload item : itensPedido) {
            Produto produtoParaBaixarQuantidade = obterProdutoPorIdCasoDeUso.retornarEntidade(item.idProduto());
            produtoParaBaixarQuantidade.diminuirQuantidade(item.quantidade());
            repositorio.save(produtoParaBaixarQuantidade);
        }
    }
}
