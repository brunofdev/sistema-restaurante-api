package com.restaurante01.api_restaurante.modulos.cardapioproduto.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso.ObterProdutoValorCostumizadoCasoDeUso;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso.ValidarEstoquePedidoUseCase;
import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemValidacaoEstoque;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.ConsultaCardapioProdutoPorta;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContratoPedidoAdaptador implements ConsultaCardapioProdutoPorta {

    private final ObterProdutoValorCostumizadoCasoDeUso obterProdutoValorCostumizadoCasoDeUso;
    private final ValidarEstoquePedidoUseCase validarEstoquePedidoUseCase;

    public ContratoPedidoAdaptador(ObterProdutoValorCostumizadoCasoDeUso obterProdutoValorCostumizadoCasoDeUso, ValidarEstoquePedidoUseCase validarEstoquePedidoUseCase) {
        this.obterProdutoValorCostumizadoCasoDeUso = obterProdutoValorCostumizadoCasoDeUso;
        this.validarEstoquePedidoUseCase = validarEstoquePedidoUseCase;
    }

    @Override
    public CardapioProduto produtoComCamposCustom(Long idCardapio, Long idProduto){
        return obterProdutoValorCostumizadoCasoDeUso.executar(idCardapio, idProduto);
    }

    @Override
    public List<CardapioProduto> validarEstoque(Long idCardapioList, List<ItemValidacaoEstoque> itens){
        return validarEstoquePedidoUseCase.executar(idCardapioList, itens);
    }

}
