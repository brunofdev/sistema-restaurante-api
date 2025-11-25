package com.restaurante01.api_restaurante.pedido.dto.entrada;

import java.util.List;

public record CriarPedidoDTO(
        List<ItemPedidoSolicitadoDTO> itens
) {}
