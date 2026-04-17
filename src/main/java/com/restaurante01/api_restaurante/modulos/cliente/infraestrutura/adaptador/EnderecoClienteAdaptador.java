package com.restaurante01.api_restaurante.modulos.cliente.infraestrutura.adaptador;


import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Endereco;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.porta.EnderecoClientePorta;
import org.springframework.stereotype.Component;

@Component
public class EnderecoClienteAdaptador implements EnderecoClientePorta{

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
}
