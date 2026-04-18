package com.restaurante01.api_restaurante.modulos.cliente.infraestrutura.adaptador;


import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Endereco;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.InformacoesClienteParaPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.ClientePorta;
import org.springframework.stereotype.Component;

@Component
public class ClientePedidoAdaptador implements ClientePorta {

    @Override
    public Endereco obterEndereco(Cliente cliente){
        return new Endereco(
                cliente.getRua(),
                cliente.getNumeroResidencia(),
                cliente.getBairro(),
                cliente.getCidade(),
                cliente.getEstado(),
                cliente.getCep(),
                cliente.getObservacaoEndereco());
    }

    @Override
    public InformacoesClienteParaPedido obterDetalhesClienteParaPedido(Cliente cliente){
        return new InformacoesClienteParaPedido(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone()
        );
    }
}
