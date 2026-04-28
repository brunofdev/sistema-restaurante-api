package com.restaurante01.api_restaurante.modulos.pedido.infraestrutura.adaptador;

import com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade.Cliente;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.repositorio.PedidoRepositorio;
import com.restaurante01.api_restaurante.modulos.pedido.infraestrutura.persistencia.PedidoJPA;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class PedidoJpaAdaptador implements PedidoRepositorio {

    private final PedidoJPA jpa;

    public PedidoJpaAdaptador(PedidoJPA jpa) {
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
    @Override
    public Optional<LocalDateTime> encontrarPedidoComCupomRecorrente (Long idCliente, String codigoCupom){
        return jpa.buscarDataUltimoUsoDoCupomPeloCliente(idCliente, codigoCupom);
    }
}