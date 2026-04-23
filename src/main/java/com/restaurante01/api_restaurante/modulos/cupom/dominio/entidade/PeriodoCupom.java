package com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade;



import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.PeriodoInvalidoExcecao;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Embeddable
public class PeriodoCupom {

    private static final DateTimeFormatter DATA_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter HORA_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm");

    @Column(name = "data_ini", nullable = false)
    private LocalDate dataInicio;
    @Column(name = "hora_ini", nullable = false)
    private LocalTime horaInicio;
    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;
    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;

    protected PeriodoCupom() {}

    public PeriodoCupom(String dataInicio, String horaInicio,
                   String dataFim,   String horaFim) {

        this.dataInicio = LocalDate.parse(dataInicio, DATA_FORMATTER);
        this.horaInicio = LocalTime.parse(horaInicio, HORA_FORMATTER);
        this.dataFim    = LocalDate.parse(dataFim,    DATA_FORMATTER);
        this.horaFim    = LocalTime.parse(horaFim,    HORA_FORMATTER);

        validar();
    }

    private void validar() {
        if (toInicio().isAfter(toFim())) {
            throw new PeriodoInvalidoExcecao("Data de início não pode ser após a data de fim");
        }
    }

    public boolean estaVigente() {
        LocalDateTime agora = LocalDateTime.now();
        return agora.isEqual(toInicio()) || agora.isEqual(toFim())
                || (agora.isAfter(toInicio()) && agora.isBefore(toFim()));
    }

    public LocalDateTime toInicio() { return LocalDateTime.of(dataInicio, horaInicio); }
    public LocalDateTime toFim()    { return LocalDateTime.of(dataFim,    horaFim);    }

}
