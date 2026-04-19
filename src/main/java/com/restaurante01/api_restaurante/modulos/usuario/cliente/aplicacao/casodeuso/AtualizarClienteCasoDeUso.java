package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.mapeador.ClienteMapper;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.validador.ClienteValidator; // Import adicionado
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio.ClienteRepositorio;
import com.restaurante01.api_restaurante.modulos.usuario.dominio.exceptions.UserDontFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizarClienteCasoDeUso {

    private final ClienteRepositorio repository;
    private final ClienteMapper mapper;
    private final ClienteValidator validator; // Validador injetado

    public AtualizarClienteCasoDeUso(ClienteRepositorio repository, ClienteMapper mapper, ClienteValidator validator) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Transactional
    public ClienteDTO executar(Long id, ClienteDTO dto) {
        Cliente clienteExistente = repository.buscarPorId(id)
                .orElseThrow(() -> new UserDontFoundException("Cliente não encontrado"));
        validator.validarAtualizacao(dto, clienteExistente);
        mapper.atualizarEntidade(clienteExistente, dto);
        repository.salvar(clienteExistente);

        return mapper.mapearClienteParaClienteDTO(clienteExistente);
    }
}