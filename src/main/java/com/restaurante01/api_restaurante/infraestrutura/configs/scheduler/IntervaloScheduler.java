package com.restaurante01.api_restaurante.infraestrutura.configs.scheduler;

public enum IntervaloScheduler {

    UM_MINUTO        (1),
    CINCO_MINUTOS    (5),
    DEZ_MINUTOS      (10),
    TRINTA_MINUTOS   (30),
    UMA_HORA         (60),
    DUAS_HORAS       (120),
    SEIS_HORAS       (360),
    DOZE_HORAS       (720),
    UM_DIA           (1440);

    private final long minutos;

    IntervaloScheduler(long minutos) {
        this.minutos = minutos;
    }

    public long emMilissegundos() {
        return minutos * 60 * 1000;
    }

    public long emSegundos() {
        return minutos * 60;
    }
}
