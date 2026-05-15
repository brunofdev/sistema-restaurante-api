package com.restaurante01.api_restaurante.modulos.avaliacao.infraestrutura.schuduler;

import com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso.DisponibilizarAvaliacaoCasoDeUso;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DisponibilizaAvaliacaoCriadaSchedulerTest {

    @Mock
    private DisponibilizarAvaliacaoCasoDeUso casoDeUso;

    @InjectMocks
    private DisponibilizaAvaliacaoCriadaScheduler scheduler;

    @Test
    @DisplayName("Quando o scheduler disparar, Entao deve delegar ao caso de uso")
    void deveDelegarAoCasoDeUso() {
        scheduler.executar();

        verify(casoDeUso).executar();
    }

    @Test
    @DisplayName("Quando o scheduler disparar, Entao nao deve chamar nada alem do caso de uso")
    void naoDeveExecutarNadaAlemDoCasoDeUso() {
        scheduler.executar();

        verify(casoDeUso).executar();
        verifyNoMoreInteractions(casoDeUso);
    }
}
