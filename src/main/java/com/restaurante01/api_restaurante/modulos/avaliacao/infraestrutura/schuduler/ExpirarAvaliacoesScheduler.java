package com.restaurante01.api_restaurante.modulos.avaliacao.infraestrutura.schuduler;

import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso.ExpirarAvaliacaoCasoDeUso;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpirarAvaliacoesScheduler {

    private final ExpirarAvaliacaoCasoDeUso casoDeUso;

    @Scheduled(fixedDelay = 60_000)
    public void executar(){
        casoDeUso.executar();
    }
}

