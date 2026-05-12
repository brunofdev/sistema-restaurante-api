package com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoEntregueClientePayload(
        Long pedidoId,
        Long clienteId,
        BigDecimal valorTotal,
        LocalDateTime data
) {
}
