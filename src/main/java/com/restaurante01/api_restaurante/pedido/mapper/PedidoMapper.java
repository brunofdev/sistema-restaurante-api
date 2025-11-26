package com.restaurante01.api_restaurante.pedido.mapper;

import com.restaurante01.api_restaurante.pedido.Enum.StatusPedido;
import com.restaurante01.api_restaurante.pedido.dto.saida.ItemPedidoDTO;
import com.restaurante01.api_restaurante.pedido.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.pedido.entity.ItemPedido;
import com.restaurante01.api_restaurante.pedido.entity.Pedido;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import com.restaurante01.api_restaurante.usuarios.entity.Usuario;
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
                pedido.getUsuario().getNome(),
                pedido.getUsuario().getCpf(),
                pedido.getUsuario().getTelefone(),
                itens,
                pedido.getValorTotal(),
                pedido.getStatusPedido()
        );
    }public List<PedidoDTO> mapearListaPedidoDTO (List<Pedido> pedidos){
        return pedidos.stream().map(this::mapearPedidoDto).toList();
    }
}
