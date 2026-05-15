package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.mapeador;

import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.saida.AvaliacaoPendenteClienteDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.saida.ItensDoPedidoSaidaDTO;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
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

    public ItensDoPedidoSaidaDTO mapearItensPedidoSaidaDTO(AvaliacaoItem item){
        return new ItensDoPedidoSaidaDTO(
                item.getId(),
                item.getNomeProdutoAvaliacao()
        );
    }
    public List<ItensDoPedidoSaidaDTO> mapearListaItensPedidoSaidaDTO(List<AvaliacaoItem> itensPedido){
        return itensPedido.stream().map(this::mapearItensPedidoSaidaDTO).collect(Collectors.toList());
    }

    public AvaliacaoPendenteClienteDTO mapearAvaliacaoPendenteCliente(Avaliacao avaliacao){
        return new AvaliacaoPendenteClienteDTO(
                avaliacao.getId(),
                avaliacao.getDataCriacao(),
                mapearListaItensPedidoSaidaDTO(avaliacao.getItensAvaliados())
        );
    }

    public List<AvaliacaoPendenteClienteDTO> mapearAvaliacoesPendentesDoCliente(List<Avaliacao> avaliacoes){
        return avaliacoes.stream().map(this::mapearAvaliacaoPendenteCliente).collect(Collectors.toList());
    }
}
