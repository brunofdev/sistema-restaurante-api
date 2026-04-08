package com.restaurante01.api_restaurante.modulos.pedido.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida.PedidoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.aplicacao.mappeador.PedidoMapper;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarPedidosPorClienteCasoDeUso {

    private final PedidoRepositorio repository;
    private final PedidoMapper mapper;

    public ListarPedidosPorClienteCasoDeUso(PedidoRepositorio repository, PedidoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<PedidoDTO> executar(Cliente cliente, Pageable pageable) {
        return repository.buscarPorCliente(cliente, pageable).map(mapper::mapearPedidoDto);
    }
}