package com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.saida;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Item de pedido avaliado individualmente pelo cliente.")
public record AvaliacaoItemDTO(

        @Schema(description = "ID do item de avaliação", example = "10")
        Long id,

        @Schema(description = "ID do produto avaliado", example = "3")
        Long produtoId,

        @Schema(description = "Nome do produto no momento da avaliação", example = "X-Bacon Duplo")
        String nomeProduto,

        @Schema(description = "Nota individual dada ao produto, de 1 a 5. Nulo se o cliente não avaliou o item.", example = "4")
        Integer nota,

        @Schema(description = "Comentário específico sobre o produto. Nulo se não informado.", example = "Muito bem temperado!")
        String comentario
) {
}
