package com.restaurante01.api_restaurante.modulos.cupom.api.dto.saida;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade.PeriodoCupom;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;

@Schema(description = "Detalhes do período de vigência do cupom")
public record PeriodoCupomSaidaDTO(

        @Schema(description = "Data de início", example = "14/04/2026")
        @JsonFormat(pattern = "dd/MM/yyyy")
        String dataInicio,

        @Schema(description = "Hora de início", example = "12:00")
        @JsonFormat(pattern = "HH:mm")
        String horaInicio,

        @Schema(description = "Data de fim", example = "14/06/2026")
        @JsonFormat(pattern = "dd/MM/yyyy")
        String dataFim,

        @Schema(description = "Hora de fim", example = "15:00")
        @JsonFormat(pattern = "HH:mm")
        String horaFim
) {
}