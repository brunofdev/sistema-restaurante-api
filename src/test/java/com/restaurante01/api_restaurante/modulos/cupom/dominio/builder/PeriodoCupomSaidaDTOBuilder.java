package com.restaurante01.api_restaurante.modulos.cupom.dominio.builder;

import com.restaurante01.api_restaurante.modulos.cupom.api.dto.saida.PeriodoCupomSaidaDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class PeriodoCupomSaidaDTOBuilder {

    private String dataInicio = "14/01/2026";
    private String horaInicio ="12:00";
    private String dataFim = "14/01/2030";
    private String horaFim = "14:00";

    public static PeriodoCupomSaidaDTOBuilder umPeriodoSaida() {
        return new PeriodoCupomSaidaDTOBuilder();
    }

    public PeriodoCupomSaidaDTOBuilder comInicio(String data, String hora) {
        this.dataInicio = data;
        this.horaInicio = hora;
        return this;
    }

    public PeriodoCupomSaidaDTOBuilder comFim(String data, String hora) {
        this.dataFim = data;
        this.horaFim = hora;
        return this;
    }


    public PeriodoCupomSaidaDTO build() {
        return new PeriodoCupomSaidaDTO(dataInicio.toString(), horaInicio.toString(), dataFim.toString(), horaFim.toString());
    }
}