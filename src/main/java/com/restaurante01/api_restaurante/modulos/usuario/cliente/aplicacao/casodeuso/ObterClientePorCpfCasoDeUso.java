package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.entidade.Cpf;
import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.exceptions.UsuarioNaoEncontradoExcecao;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.mapeador.ClienteMapeador;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio.ClienteRepositorio;
import org.springframework.stereotype.Service;

@Service
public class ObterClientePorCpfCasoDeUso {

    private final ClienteRepositorio repository;
    private final ClienteMapeador mapper;

    public ObterClientePorCpfCasoDeUso(ClienteRepositorio repository, ClienteMapeador mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Cliente retornarEntidade(String cpf) {
        return repository
                .buscarPorCpf(new Cpf(cpf))
                .orElseThrow(() -> new UsuarioNaoEncontradoExcecao("Cliente não encontrado com o CPF informado"));
    }

    public ClienteDTO retornarDTO(String cpf) {
        return mapper.mapearClienteParaClienteDTO(retornarEntidade(cpf));
    }
}