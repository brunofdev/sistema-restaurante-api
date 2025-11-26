package com.restaurante01.api_restaurante.pedido.dto.entrada;

import java.util.List;

public record pedidoCriacaoDTO(
        List<ItemPedidoSolicitadoDTO> itens
) {}
