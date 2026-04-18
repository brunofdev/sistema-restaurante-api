package com.restaurante01.api_restaurante.modulos.cliente.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.excecao.ClienteNaoEncontradoExcecao;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.repositorio.ClienteRepositorio;
import org.springframework.stereotype.Component;

@Component
public class BuscarClientePorIdCasoDeUso {
    private final ClienteRepositorio clienteRepositorio;

    public BuscarClientePorIdCasoDeUso(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }


    protected Cliente executar(Long id){
        return clienteRepositorio.buscarPorId(id).orElseThrow(() -> new ClienteNaoEncontradoExcecao("Cliente com o id: " + id + " não localizado"));
    }
}
