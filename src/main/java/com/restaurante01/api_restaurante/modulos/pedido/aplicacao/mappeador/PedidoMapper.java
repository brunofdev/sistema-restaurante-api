package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mappeador;

import com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.entidade.CardapioProduto;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.ItemPedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class PedidoMapper {
    //regra para priorizar preço custumozidado
    public ItemPedido mapearItemPedido (Integer quantidade, CardapioProduto produto, String observacao){
        BigDecimal preco = (produto.getPrecoCustomizado() == null || produto.getPrecoCustomizado().compareTo(BigDecimal.ZERO) <= 0) ? produto.getProduto().getPreco() : produto.getPrecoCustomizado();
        return new ItemPedido(
                produto.getProduto(),
                quantidade,
                preco,
                observacao
                );
    }
    public ItemPedidoDTO mapearItemPedidoDto (ItemPedido item){
        return new ItemPedidoDTO (
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getObservacao(),
                item.calcularSubTotal()
        );
    }
    public PedidoDTO mapearPedidoDto (Pedido pedido){
        List<ItemPedidoDTO> itens = new ArrayList<>();
        pedido.getItens().forEach(item -> itens.add(mapearItemPedidoDto(item)));
        return new PedidoDTO(
                pedido.getId(),
                pedido.getCliente().getNome(),
                pedido.getCliente().getCpf(),
                pedido.getCliente().getTelefone(),
                itens,
                pedido.getValorTotal(),
                pedido.getStatusPedido()
        );
    }public List<PedidoDTO> mapearListaPedidoDTO (List<Pedido> pedidos){
        return pedidos.stream().map(this::mapearPedidoDto).toList();
    }
}
