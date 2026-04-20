package com.restaurante01.api_restaurante.modulos.cardapio.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso.ObterProdutoValorCostumizadoCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapio.aplicacao.casodeuso.associacao.casodeuso.ValidarEstoquePedidoUseCase;
import com.restaurante01.api_restaurante.modulos.cardapio.dominio.entidade.Associacao;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ItemValidacaoEstoque;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ProdutoVendido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.RepresentacaoProdutoItemPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoAssociacaoPorta;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssociacaoPedidoAdaptador implements PedidoAssociacaoPorta {

    private final ObterProdutoValorCostumizadoCasoDeUso obterProdutoValorCostumizadoCasoDeUso;
    private final ValidarEstoquePedidoUseCase validarEstoquePedidoUseCase;

    public AssociacaoPedidoAdaptador(ObterProdutoValorCostumizadoCasoDeUso obterProdutoValorCostumizadoCasoDeUso, ValidarEstoquePedidoUseCase validarEstoquePedidoUseCase) {
        this.obterProdutoValorCostumizadoCasoDeUso = obterProdutoValorCostumizadoCasoDeUso;
        this.validarEstoquePedidoUseCase = validarEstoquePedidoUseCase;
    }

    @Override
    public ProdutoVendido obterProdutoVendido(Long idCardapio, Long idProduto){
        Associacao associacao = obterProdutoValorCostumizadoCasoDeUso.executar(idCardapio, idProduto);
        return new ProdutoVendido(new RepresentacaoProdutoItemPedido(
                associacao.getProduto().getId(),
                associacao.getProduto().getNome()),
                associacao.resolverPrecoDeVenda()
        );
    }

    @Override
    public void validarEstoque(Long idCardapioList, List<ItemValidacaoEstoque> itens){
        validarEstoquePedidoUseCase.executar(idCardapioList, itens);
    }

}
