package com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio;

import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PedidoRepositorio {
    Pedido salvar(Pedido pedido);
    Optional<Pedido> buscarPorId(Long id);
    Page<Pedido> buscarTodos(Pageable pageable);
    Page<Pedido> buscarPorCliente(Cliente cliente, Pageable pageable);
    Page<Pedido> buscarPorDataCriacaoEntre(LocalDateTime inicio, LocalDateTime fim, Pageable pageable);
}

