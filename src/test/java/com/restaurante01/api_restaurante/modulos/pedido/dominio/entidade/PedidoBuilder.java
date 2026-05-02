package com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.EnderecoPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.InformacoesClienteParaPedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoBuilder {

    private Long idCardapio = 1L;
    private InformacoesClienteParaPedido cliente = new InformacoesClienteParaPedido(
            1L, "Cliente Teste", "000.000.000-00", "51999999999"
    );
    private EnderecoPedido endereco = new EnderecoPedido(
            "Rua Teste", 100, "Bairro Teste", "Porto Alegre", "RS", "90000-000", null
    );
    private List<ItemPedido> itens = new ArrayList<>();

    public static PedidoBuilder umPedido() {
        return new PedidoBuilder();
    }

    public PedidoBuilder comIdCardapio(Long idCardapio) {
        this.idCardapio = idCardapio;
        return this;
    }

    public PedidoBuilder comCliente(InformacoesClienteParaPedido cliente) {
        this.cliente = cliente;
        return this;
    }

    public PedidoBuilder comEndereco(EnderecoPedido endereco) {
        this.endereco = endereco;
        return this;
    }

    public PedidoBuilder comItem(ItemPedido item) {
        this.itens.add(item);
        return this;
    }

    public PedidoBuilder comItens(List<ItemPedido> itens) {
        this.itens.addAll(itens);
        return this;
    }

    public Pedido build() {
        Pedido pedido = Pedido.criar(idCardapio, cliente, endereco);
        itens.forEach(pedido::adicionarItem);
        return pedido;
    }
}
