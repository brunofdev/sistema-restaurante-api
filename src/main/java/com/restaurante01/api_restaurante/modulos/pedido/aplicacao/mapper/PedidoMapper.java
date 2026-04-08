package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mapper;

import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.ItemPedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.produto.dominio.entidade.Produto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PedidoMapper {
    public ItemPedido mapearItemPedido (Integer quantidade, Produto produto){
        return new ItemPedido(
                produto,
                quantidade,
                produto.getPreco()
                );
    }
    public ItemPedidoDTO mapearItemPedidoDto (ItemPedido item){
        return new ItemPedidoDTO (
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
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
