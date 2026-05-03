package com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida;

import java.time.LocalDate;
import java.util.List;

public record ItensMaisVendidosPorPeriodo(
        LocalDate doDia,
        LocalDate ateDia,
        List<ItemPedidoMaisVendidoSemanal> itens
) {
}
