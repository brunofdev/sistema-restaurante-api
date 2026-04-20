package com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.aplicacao.mapeador.ClienteMapeador;
import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.repositorio.ClienteRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarTodosClientesCasoDeUso {

    private final ClienteRepositorio repository;
    private final ClienteMapeador mapper;

    public ListarTodosClientesCasoDeUso(ClienteRepositorio repository, ClienteMapeador mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<ClienteDTO> executar(Pageable pageable) {
        return repository.buscarTodos(pageable)
                .map(mapper::mapearClienteParaClienteDTO);
    }
}