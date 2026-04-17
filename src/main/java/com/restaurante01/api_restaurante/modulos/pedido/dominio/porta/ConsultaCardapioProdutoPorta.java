package com.restaurante01.api_restaurante.modulos.pedido.dominio.porta;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemValidacaoEstoque;

import java.util.List;

public interface ConsultaCardapioProdutoPorta {
    CardapioProduto produtoComCamposCustom(Long idCardapio, Long idProduto);
    List<CardapioProduto> validarEstoque(Long idCardapio, List<ItemValidacaoEstoque> itens);
}
