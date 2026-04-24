package com.restaurante01.api_restaurante.builders;

import com.restaurante01.api_restaurante.modulos.cupom.api.dto.entrada.PeriodoCupomDTO;

public class PeriodoCupomDTOBuilder {

    private String dataInicio = "14/04/2026";
    private String horaInicio = "12:00";
    private String dataFim = "14/06/2026";
    private String horaFim = "15:00";

    public static PeriodoCupomDTOBuilder umPeriodo() {
        return new PeriodoCupomDTOBuilder();
    }

    public PeriodoCupomDTOBuilder comInicio(String data, String hora) {
        this.dataInicio = data;
        this.horaInicio = hora;
        return this;
    }

    public PeriodoCupomDTOBuilder comFim(String data, String hora) {
        this.dataFim = data;
        this.horaFim = hora;
        return this;
    }

    public PeriodoCupomDTOBuilder expirado() {
        this.dataInicio = "01/01/2020";
        this.dataFim = "01/02/2020";
        return this;
    }

    public PeriodoCupomDTO build() {
        return new PeriodoCupomDTO(dataInicio, horaInicio, dataFim, horaFim);
    }
}