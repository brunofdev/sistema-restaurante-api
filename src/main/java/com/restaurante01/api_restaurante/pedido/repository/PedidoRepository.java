package com.restaurante01.api_restaurante.pedido.repository;

import com.restaurante01.api_restaurante.pedido.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
}
