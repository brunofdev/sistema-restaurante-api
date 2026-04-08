package com.restaurante01.api_restaurante.modulos.operador.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.operador.api.dto.saida.OperadorDTO;
import com.restaurante01.api_restaurante.modulos.operador.aplicacao.mapper.OperadorMapper;
import com.restaurante01.api_restaurante.modulos.operador.dominio.repositorio.OperadorRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarTodosOperadoresCasoDeUso {

    private final OperadorRepositorio repository;
    private final OperadorMapper mapper;

    public ListarTodosOperadoresCasoDeUso(OperadorRepositorio repository, OperadorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<OperadorDTO> executar(Pageable pageable) {
        return repository.buscarTodos(pageable)
                .map(mapper::mapearOperadorParaOperadorDTO);
    }
}