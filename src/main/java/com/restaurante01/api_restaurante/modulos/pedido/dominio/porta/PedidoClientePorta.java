package com.restaurante01.api_restaurante.modulos.pedido.dominio.porta;

import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.Endereco;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.InformacoesClienteParaPedido;

public interface PedidoClientePorta {
    Endereco obterEndereco(Cliente cliente);
    InformacoesClienteParaPedido obterDetalhesClienteParaPedido(Cliente cliente);
}
