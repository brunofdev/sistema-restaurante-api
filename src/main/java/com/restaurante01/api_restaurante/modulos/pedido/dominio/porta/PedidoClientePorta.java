package com.restaurante01.api_restaurante.modulos.pedido.dominio.porta;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.EnderecoPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.InformacoesClienteParaPedido;

public interface PedidoClientePorta {
    EnderecoPedido obterEndereco(Cliente cliente);
    InformacoesClienteParaPedido obterDetalhesClienteParaPedido(Cliente cliente);
}
