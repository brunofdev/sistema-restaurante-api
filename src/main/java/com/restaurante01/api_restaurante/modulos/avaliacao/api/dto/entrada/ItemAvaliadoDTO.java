package com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ItemAvaliadoDTO(

        @Schema(description = "Id do item de avaliação (AvaliacaoItem).", example = "1")
        @NotNull(message = "O id do item avaliado é obrigatório.")
        Long idDoItemAvaliado,

        @Schema(description = "Id do produto avaliado.", example = "3")
        @NotNull(message = "O id do produto avaliado é obrigatório.")
        Long idDoProdutoAvaliado,

        @Schema(description = "Avaliação do item. Obrigatório — se não quer avaliar o item, simplesmente não o inclua na lista.")
        @NotNull(message = "A avaliação do item é obrigatória. Se não deseja avaliá-lo, não o inclua na lista.")
        @Valid
        AvaliacaoDTO avaliacaoDTO
) {
}
