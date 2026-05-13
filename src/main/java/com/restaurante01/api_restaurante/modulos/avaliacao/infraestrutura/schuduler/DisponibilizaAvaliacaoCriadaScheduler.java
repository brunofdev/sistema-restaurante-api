package com.restaurante01.api_restaurante.modulos.avaliacao.infraestrutura.schuduler;

import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso.DisponibilizarAvaliacaoCasoDeUso;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DisponibilizaAvaliacaoCriadaScheduler {

    private final DisponibilizarAvaliacaoCasoDeUso casoDeUso;

    @Scheduled(fixedDelay = 60_000)
    public void executar(){
        casoDeUso.executar();
    }


}
