package com.restaurante01.api_restaurante.modulos.avaliacao.infraestrutura.schuduler;

import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso.RenotificarClienteCasoDeUso;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RenotificarAvaliacoesDisponiveisScheduler {

    private final RenotificarClienteCasoDeUso casoDeUso;

    @Scheduled(fixedDelay = 60_000)
    public void executar(){
        casoDeUso.executar();
    }

}
