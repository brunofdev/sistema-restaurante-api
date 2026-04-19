package com.restaurante01.api_restaurante.modulos.usuario.cliente.infraestrutura.adaptador;


import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.EnderecoPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto.InformacoesClienteParaPedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.PedidoClientePorta;
import org.springframework.stereotype.Component;

@Component
public class PedidoClientePedidoAdaptador implements PedidoClientePorta {

    @Override
    public EnderecoPedido obterEndereco(Cliente cliente){
        return new EnderecoPedido(
                cliente.getEnderecoCliente().rua(),
                cliente.getEnderecoCliente().numero(),
                cliente.getEnderecoCliente().bairro(),
                cliente.getEnderecoCliente().cidade(),
                cliente.getEnderecoCliente().estado(),
                cliente.getEnderecoCliente().cep(),
                cliente.getEnderecoCliente().referencia());
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
