package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mapeador;

import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.EnderecoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.ItemPedidoSolicitadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.ItemPedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Endereco;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemValidacaoEstoque;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PedidoMapper {
    public List<ItemValidacaoEstoque> mapearParaValidacaoDeEstoque(List<ItemPedidoSolicitadoDTO> itensPedidoDTO){
        return  itensPedidoDTO.stream()
                .map(item -> new ItemValidacaoEstoque(item.idProduto(), item.quantidade()))
                .toList();
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
                pedido.getStatusPedido(),
                mapearEndereco(pedido.getEnderecoEntrega())
        );
    }
    private EnderecoDTO mapearEndereco (Endereco endereco){
        return new EnderecoDTO(
                endereco.rua(),
                endereco.numero(),
                endereco.bairro(),
                endereco.cidade(),
                endereco.estado(),
                endereco.cep(),
                endereco.referencia()
        );
    }
}
