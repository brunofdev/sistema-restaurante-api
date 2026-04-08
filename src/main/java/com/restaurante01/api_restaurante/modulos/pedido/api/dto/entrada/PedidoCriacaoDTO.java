package com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada;

import java.util.List;

public record PedidoCriacaoDTO(
        List<ItemPedidoSolicitadoDTO> itens
) {}
