package com.restaurante01.api_restaurante.modulos.pedido.infraestrutura.persistencia;

import com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade.Pedido;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoJPA extends JpaRepository<Pedido, Long> {
    Page<Pedido> findByCliente_ClienteId(Long clienteId, Pageable pageable);
    Page<Pedido> findByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim, Pageable pageable);
    List<Pedido> findByStatusPedidoOrderByDataCriacao(StatusPedido statusPedido);
    List<Pedido> findByStatusPedidoNot(StatusPedido statusPedido);
    List<Pedido> findByStatusPedidoAndDataAtualizacaoAfter(StatusPedido statusPedido, LocalDateTime limite);

    @Query(
            """

                    SELECT p.dataCriacao FROM Pedido p
    WHERE p.cliente.clienteId = :clienteId
    AND p.cupom.codigoCupom = :codigoCupom
    ORDER BY p.dataCriacao DESC
    LIMIT 1
    """)
    Optional<LocalDateTime> buscarDataUltimoUsoDoCupomPeloCliente(
            @Param("clienteId") Long clienteId,
            @Param("codigoCupom") String codigoCupom);
    }