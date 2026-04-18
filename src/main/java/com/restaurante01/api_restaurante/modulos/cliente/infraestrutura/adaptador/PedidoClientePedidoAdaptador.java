package com.restaurante01.api_restaurante.modulos.cliente.infraestrutura.adaptador;


import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.Endereco;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.InformacoesClienteParaPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoClientePorta;
import org.springframework.stereotype.Component;

@Component
public class PedidoClientePedidoAdaptador implements PedidoClientePorta {

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
