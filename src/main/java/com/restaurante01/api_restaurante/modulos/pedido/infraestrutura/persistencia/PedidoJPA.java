package com.restaurante01.api_restaurante.modulos.pedido.infraestrutura.persistencia;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.cliente.dominio.entidade.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PedidoJPA extends JpaRepository<Pedido, Long>{
    Page<Pedido> findByCliente(Cliente cliente, Pageable pageable);
    Page<Pedido> findByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim, Pageable pageable);
}
