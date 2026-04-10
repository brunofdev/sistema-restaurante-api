package com.restaurante01.api_restaurante.modulos.cardapioproduto.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.produto.aplicacao.casodeuso.AtualizarUmProdutoCasoDeUso;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class BaixarQuantidadeEstoqueCasoDeUso {

    private final AtualizarCamposCustomDaAssociacaoCasoDeUso atualizarCamposCustomDaAssociacaoCasoDeUso;
    private final AtualizarUmProdutoCasoDeUso atualizarUmProdutoCasoDeUso;

    public BaixarQuantidadeEstoqueCasoDeUso(AtualizarUmProdutoCasoDeUso atualizarUmProdutoCasoDeUso,
                                            AtualizarCamposCustomDaAssociacaoCasoDeUso atualizarCamposCustomDaAssociacaoCasoDeUso) {
        this.atualizarUmProdutoCasoDeUso = atualizarUmProdutoCasoDeUso;
        this.atualizarCamposCustomDaAssociacaoCasoDeUso = atualizarCamposCustomDaAssociacaoCasoDeUso;
    }
    public void executar(List<CardapioProduto> estoqueParaBaixar, Pedido pedido, Long idCardapio) {
        for (ItemPedido item : pedido.getItens()) {
            atualizarCamposCustomDaAssociacaoCasoDeUso.executar2(idCardapio, item);
            atualizarUmProdutoCasoDeUso.executar2(item.getProduto().getId(), item.getQuantidade());
            System.out.println("teste");
        }
    }
}
