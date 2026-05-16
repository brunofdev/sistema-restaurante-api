package com.restaurante01.api_restaurante.modulos.avaliacao.api.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record AvaliacaoDTO(

        @Schema(description = "Nota de 1 a 5. Nulo representa voto em branco.", example = "5")
        @Min(value = 1, message = "A nota deve ser no mínimo 1.")
        @Max(value = 5, message = "A nota deve ser no máximo 5.")
        Integer nota,

        @Schema(description = "Comentário opcional. Se informado, não pode ser vazio nem ultrapassar 500 caracteres.", example = "Chegou quentinho e no prazo!")
        @Size(min = 1, max = 500, message = "O comentário deve ter entre 1 e 500 caracteres.")
        String comentario
) {
}
