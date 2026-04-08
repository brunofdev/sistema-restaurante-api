package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mapper.PedidoMapper;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarTodosPedidosCasoDeUso {

    private final PedidoRepositorio repository;
    private final PedidoMapper mapper;

    public ListarTodosPedidosCasoDeUso(PedidoRepositorio repository, PedidoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<PedidoDTO> executar(Pageable pageable) {
        return repository.buscarTodos(pageable).map(mapper::mapearPedidoDto);
    }
}