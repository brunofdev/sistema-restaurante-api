package com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoSolicitadoDTO(

        @Schema(description = "Id do produto selecionado", example = "1")
        @NotNull(message = "id não pode ser vazio")
        Long idProduto,

        @Schema(description = "Quantidade desejada do produto", example = "1")
        @NotNull(message = "A quantidade não pode ser vazia")
        Integer quantidade,

        @Schema(description = "Observação sobre o item selecionado", example = "Sem cebola")
        String observacao
) {
}
