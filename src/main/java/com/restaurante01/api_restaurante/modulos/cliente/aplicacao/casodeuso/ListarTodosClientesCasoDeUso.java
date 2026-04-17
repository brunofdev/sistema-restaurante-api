package com.restaurante01.api_restaurante.modulos.cliente.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cliente.api.dto.saida.ClienteDTO;
import com.restaurante01.api_restaurante.modulos.cliente.aplicacao.mapeador.ClienteMapper;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.repositorio.ClienteRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarTodosClientesCasoDeUso {

    private final ClienteRepositorio repository;
    private final ClienteMapper mapper;

    public ListarTodosClientesCasoDeUso(ClienteRepositorio repository, ClienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<ClienteDTO> executar(Pageable pageable) {
        return repository.buscarTodos(pageable)
                .map(mapper::mapearClienteParaClienteDTO);
    }
}