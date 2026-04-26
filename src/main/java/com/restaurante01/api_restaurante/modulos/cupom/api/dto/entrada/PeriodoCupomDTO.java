package com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Objeto de entrada contendo as datas e horários de validade do cupom.")
public record PeriodoCupomDTO(

        @Schema(description = "Data de início da validade no formato DD/MM/AAAA.", example = "14/04/2026")
        @NotBlank(message = "A data de início é obrigatória")
        @Pattern(
                regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/\\d{4}$",
                message = "A data de início deve ser válida e seguir o formato DD/MM/AAAA"
        )
        String dataInicio,

        @Schema(description = "Hora de início da validade no formato HH:MM (24 horas).", example = "12:00")
        @NotBlank(message = "A hora de início é obrigatória")
        @Pattern(
                regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$",
                message = "A hora de início deve ser válida (00:00 a 23:59) e seguir o formato HH:MM"
        )
        String horaInicio,

        @Schema(description = "Data de término da validade no formato DD/MM/AAAA.", example = "14/06/2026")
        @NotBlank(message = "A data de fim é obrigatória")
        @Pattern(
                regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/\\d{4}$",
                message = "A data de fim deve ser válida e seguir o formato DD/MM/AAAA"
        )
        String dataFim,

        @Schema(description = "Hora de término da validade no formato HH:MM (24 horas).", example = "15:00")
        @NotBlank(message = "A hora de fim é obrigatória")
        @Pattern(
                regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$",
                message = "A hora de fim deve ser válida (00:00 a 23:59) e seguir o formato HH:MM"
        )
        String horaFim
) {}