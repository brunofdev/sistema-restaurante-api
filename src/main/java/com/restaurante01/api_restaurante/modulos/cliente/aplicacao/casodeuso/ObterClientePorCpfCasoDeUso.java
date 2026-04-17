package com.restaurante01.api_restaurante.modulos.cliente.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.exceptions.UserDontFoundException;
import com.restaurante01.api_restaurante.modulos.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.cliente.aplicacao.mapeador.ClienteMapper;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.repositorio.ClienteRepositorio;
import org.springframework.stereotype.Service;

@Service
public class ObterClientePorCpfCasoDeUso {

    private final ClienteRepositorio repository;
    private final ClienteMapper mapper;

    public ObterClientePorCpfCasoDeUso(ClienteRepositorio repository, ClienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Cliente retornarEntidade(String cpf) {
        return repository
                .buscarPorCpf(cpf)
                .orElseThrow(() -> new UserDontFoundException("Cliente não encontrado com o CPF informado"));
    }

    public ClienteDTO retornarDTO(String cpf) {
        return mapper.mapearClienteParaClienteDTO(retornarEntidade(cpf));
    }
}