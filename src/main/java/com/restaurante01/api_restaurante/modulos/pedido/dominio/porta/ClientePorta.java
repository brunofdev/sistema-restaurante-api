package com.restaurante01.api_restaurante.modulos.pedido.dominio.porta;

import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Endereco;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.InformacoesClienteParaPedido;

public interface ClientePorta {
    Endereco obterEndereco(Cliente cliente);
    InformacoesClienteParaPedido obterDetalhesClienteParaPedido(Cliente cliente);
}
