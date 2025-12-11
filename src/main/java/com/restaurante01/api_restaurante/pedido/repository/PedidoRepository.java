package com.restaurante01.api_restaurante.pedido.repository;

import com.restaurante01.api_restaurante.pedido.entity.Pedido;
import com.restaurante01.api_restaurante.usuarios.cliente.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    Page<Pedido> findByCliente(Cliente cliente, Pageable pageable);
    Page<Pedido> findByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim, Pageable pageable);
}
