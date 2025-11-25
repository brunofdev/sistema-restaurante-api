package com.restaurante01.api_restaurante.pedido.mapper;

import com.restaurante01.api_restaurante.pedido.dto.entrada.CriarPedidoDTO;
import com.restaurante01.api_restaurante.pedido.dto.entrada.ItemPedidoSolicitadoDTO;
import com.restaurante01.api_restaurante.pedido.dto.saida.ItemPedidoDTO;
import com.restaurante01.api_restaurante.pedido.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.pedido.entity.ItemPedido;
import com.restaurante01.api_restaurante.pedido.entity.Pedido;
import com.restaurante01.api_restaurante.produto.entity.Produto;
import com.restaurante01.api_restaurante.usuarios.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {


    public Pedido mapearPedido(CriarPedidoDTO criarPedidoDTO, Usuario usuario, Produto produto, List<ItemPedido> itens){
        Pedido pedido = new Pedido();
        pedido.


        return new Pedido();
    }
    public ItemPedido mapearItemPedido (ItemPedidoSolicitadoDTO dto, Produto produto){
        return new ItemPedido(
                null,
                null,
                produto,
                dto.quantidade(),
                produto.getPreco()
                );
    }
    public List<ItemPedido> mapearItemPedido(List<ItemPedidoSolicitadoDTO> itens){
        return mapearItemPedido(itens).stream().map(item -> )

    }
}
