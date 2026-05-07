package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mapeador;

import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.EnderecoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.ItemPedidoSolicitadoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.*;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.*;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.ItemPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PedidoMapeador {
    public List<ItemValidacaoEstoque> mapearParaValidacaoDeEstoque(List<ItemPedidoSolicitadoDTO> itensPedidoDTO){
        return  itensPedidoDTO.stream()
                .map(item -> new ItemValidacaoEstoque(item.idProduto(), item.quantidade()))
                .toList();
    }

    public ItemPedidoDTO mapearItemPedidoDto (ItemPedido item){
        return new ItemPedidoDTO (
                item.getProduto().nome(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getObservacao(),
                item.calcularSubTotal()
        );
    }
    public PedidoCriadoDTO mapearPedidoCriadoDto(Pedido pedido){
        List<ItemPedidoDTO> itens = new ArrayList<>();
        pedido.getItens().forEach(item -> itens.add(mapearItemPedidoDto(item)));
        return new PedidoCriadoDTO(
                pedido.getId(),
                pedido.getCliente().nome(),
                pedido.getCliente().cpf(),
                pedido.getCliente().telefone(),
                itens,
                mapearValoresPedidoDTO(pedido.getValores()),
                pedido.getStatusPedido(),
                mapearEndereco(pedido.getEnderecoPedidoEntrega()),
                (pedido.getCupom() == null ? "Nenhum cupom aplicado" : pedido.getCupom().codigoCupom())
        );
    }
    public PedidoDetalhadoDTO mapearPedidoDetalhadoDto (Pedido pedido){
        List<ItemPedidoDTO> itens = new ArrayList<>();
        pedido.getItens().forEach(item -> itens.add(mapearItemPedidoDto(item)));
        return new PedidoDetalhadoDTO(
                pedido.getId(),
                pedido.getCliente().nome(),
                pedido.getCliente().cpf(),
                pedido.getCliente().telefone(),
                itens,
                mapearValoresPedidoDTO(pedido.getValores()),
                pedido.getStatusPedido(),
                mapearEndereco(pedido.getEnderecoPedidoEntrega()),
                (pedido.getCupom() == null ? null : mapearInformacoesCupomDTO(pedido.getCupom())),
                pedido.getDataCriacao()//impede de tentar mapear um cupom null
        );
    }
    private ValoresCalculoPedidoDTO mapearValoresPedidoDTO(ValoresPedido valores){
        return new ValoresCalculoPedidoDTO(
                valores.getDescontoAplicado(),
                valores.getValorBruto(),
                valores.getValorTotal()
        );
    }
    private InformacoesCupomDTO mapearInformacoesCupomDTO(CupomConsumido cupom){
        return new InformacoesCupomDTO(
                cupom.idCupom(),
                cupom.codigoCupom(),
                cupom.valorParaDesconto(),
                cupom.regraDoCupom(),
                cupom.regraRecorrencia()
        );
    }
    private EnderecoDTO mapearEndereco (EnderecoPedido enderecoPedido){
        return new EnderecoDTO(
                enderecoPedido.rua(),
                enderecoPedido.numero(),
                enderecoPedido.bairro(),
                enderecoPedido.cidade(),
                enderecoPedido.estado(),
                enderecoPedido.cep(),
                (enderecoPedido.referencia() == null || enderecoPedido.referencia().isBlank() ? "Cliente não informou uma referencia" : enderecoPedido.referencia())
        );
    }
    public EnderecoPedido mapearEndereco (EnderecoDTO dto){
            return new EnderecoPedido(
                    dto.rua(),
                    dto.numero(),
                    dto.bairro(),
                    dto.cidade(),
                    dto.estado(),
                    dto.cep(),
                    dto.referencia()
            );
        }
    public List<ItemPedidoPayload> mapearItemPedidoPayload(List<ItemPedido> itens){
        return itens.stream().map(item -> new ItemPedidoPayload(item.getProduto().idProduto(), item.getQuantidade())).toList();
    }
}
