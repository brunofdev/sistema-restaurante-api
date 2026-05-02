package com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida;

import java.util.List;

public record ItensMaisVendidosNaSemana(
        String doDia,
        String ateDia,
        List<ItemPedidoMaisVendidoSemanal> itens
) {
}
