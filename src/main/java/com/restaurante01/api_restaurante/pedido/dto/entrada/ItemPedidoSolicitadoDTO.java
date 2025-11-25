package com.restaurante01.api_restaurante.pedido.dto.entrada;

import jakarta.validation.constraints.NotNull;

public record ItemPedidoSolicitadoDTO(

        @NotNull(message = "id não pode ser vazio")
        Long idProduto,

        @NotNull(message = "A quantidade não pode ser vazia")
        Integer quantidade
) {
}
