package com.restaurante01.api_restaurante.modulos.cardapioproduto.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso.ObterProdutoValorCostumizadoCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso.ValidarEstoquePedidoUseCase;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ItemValidacaoEstoque;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ProdutoVendido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.RepresentacaoProdutoItemPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoCardapioProdutoPorta;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardapioProdutoPedidoAdaptador implements PedidoCardapioProdutoPorta {

    private final ObterProdutoValorCostumizadoCasoDeUso obterProdutoValorCostumizadoCasoDeUso;
    private final ValidarEstoquePedidoUseCase validarEstoquePedidoUseCase;

    public CardapioProdutoPedidoAdaptador(ObterProdutoValorCostumizadoCasoDeUso obterProdutoValorCostumizadoCasoDeUso, ValidarEstoquePedidoUseCase validarEstoquePedidoUseCase) {
        this.obterProdutoValorCostumizadoCasoDeUso = obterProdutoValorCostumizadoCasoDeUso;
        this.validarEstoquePedidoUseCase = validarEstoquePedidoUseCase;
    }

    @Override
    public ProdutoVendido obterProdutoVendido(Long idCardapio, Long idProduto){
        CardapioProduto cardapioProduto = obterProdutoValorCostumizadoCasoDeUso.executar(idCardapio, idProduto);
        return new ProdutoVendido(new RepresentacaoProdutoItemPedido(
                cardapioProduto.getProduto().getId(),
                cardapioProduto.getProduto().getNome()),
                cardapioProduto.resolverPrecoDeVenda()
        );
    }

    @Override
    public void validarEstoque(Long idCardapioList, List<ItemValidacaoEstoque> itens){
        validarEstoquePedidoUseCase.executar(idCardapioList, itens);
    }

}
