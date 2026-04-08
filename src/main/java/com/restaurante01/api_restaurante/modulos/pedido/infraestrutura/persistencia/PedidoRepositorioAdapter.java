package com.restaurante01.api_restaurante.modulos.pedido.infraestrutura.persistencia;

import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class PedidoRepositorioAdapter implements PedidoRepositorio {

    private final PedidoJPA jpa;

    public PedidoRepositorioAdapter(PedidoJPA jpa) {
        this.jpa = jpa;
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        return jpa.save(pedido);
    }

    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return jpa.findById(id);
    }

    @Override
    public Page<Pedido> buscarTodos(Pageable pageable) {
        return jpa.findAll(pageable);
    }

    @Override
    public Page<Pedido> buscarPorCliente(Cliente cliente, Pageable pageable) {
        return jpa.findByCliente(cliente, pageable);
    }

    @Override
    public Page<Pedido> buscarPorDataCriacaoEntre(LocalDateTime inicio, LocalDateTime fim, Pageable pageable) {
        return jpa.findByDataCriacaoBetween(inicio, fim, pageable);
    }
}