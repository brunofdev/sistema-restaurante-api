package com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ResponderAvaliacaoDTO(

        @Schema(description = "Id da avaliação a ser concluída.", example = "1")
        @NotNull(message = "O id da avaliação é obrigatório.")
        Long idAvaliacao,

        @Schema(description = "Avaliação geral do pedido. Nulo representa voto em branco.")
        @Valid
        AvaliacaoDTO avaliacaoDTO,

        @Schema(description = "Lista de avaliações por item. Nulo ou vazio significa que nenhum item foi avaliado individualmente.")
        @Valid
        List<ItemAvaliadoDTO> itensAvaliados
) {
}
