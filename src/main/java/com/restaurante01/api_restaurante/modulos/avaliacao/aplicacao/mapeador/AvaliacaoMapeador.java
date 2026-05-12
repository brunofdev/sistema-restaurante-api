package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.mapeador;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.AvaliacaoItem;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.ItemPedidoAvaliacaoPayload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AvaliacaoMapeador {

    public AvaliacaoItem mapearItemPedido(ItemPedidoAvaliacaoPayload itemPedido) {
        return AvaliacaoItem.criar(
                itemPedido.idProduto(),
                itemPedido.nome(),
                null,
                null
        );
    }

    public List<AvaliacaoItem> mapearItensPedido(List<ItemPedidoAvaliacaoPayload> itensPedido) {
        return itensPedido.stream()
                .collect(Collectors.groupingBy(ItemPedidoAvaliacaoPayload::idProduto))
                .values().stream()
                .map(itemPedidos -> mapearItemPedido(itemPedidos.get(0)))
                .toList();
        }
    }
