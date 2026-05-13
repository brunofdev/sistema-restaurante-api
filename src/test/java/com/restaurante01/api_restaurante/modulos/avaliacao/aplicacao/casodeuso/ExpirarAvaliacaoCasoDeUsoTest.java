package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.casodeuso;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.Avaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.entidade.AvaliacaoBuilder;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.StatusAvaliacao;
import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.repositorio.AvaliacaoRepositorio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpirarAvaliacaoCasoDeUsoTest {

    @Mock
    private AvaliacaoRepositorio repositorio;

    @InjectMocks
    private ExpirarAvaliacaoCasoDeUso casoDeUso;

    @Captor
    private ArgumentCaptor<List<Avaliacao>> listaCaptor;

    @Test
    @DisplayName("Dado que nao ha avaliacoes expiradas, Quando executar, Entao nao deve salvar nada")
    void naoDeveSalvarSeListaVazia() {
        when(repositorio.buscarExpiradas(eq(StatusAvaliacao.DISPONIVEL), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        casoDeUso.executar();

        verify(repositorio, never()).salvarLista(any());
    }

    @Test
    @DisplayName("Dado uma avaliacao DISPONIVEL expirada, Quando executar, Entao deve atualizar para EXPIRADA e salvar")
    void deveExpirarUmaAvaliacao() {
        Avaliacao avaliacao = AvaliacaoBuilder.umaAvaliacao().construir();
        ReflectionTestUtils.setField(avaliacao, "status", StatusAvaliacao.DISPONIVEL);

        when(repositorio.buscarExpiradas(eq(StatusAvaliacao.DISPONIVEL), any(LocalDateTime.class)))
                .thenReturn(List.of(avaliacao));

        casoDeUso.executar();

        assertThat(avaliacao.getStatus()).isEqualTo(StatusAvaliacao.EXPIRADA);
        verify(repositorio).salvarLista(List.of(avaliacao));
    }

    @Test
    @DisplayName("Dado multiplas avaliacoes DISPONIVEL expiradas, Quando executar, Entao deve atualizar todas para EXPIRADA")
    void deveExpirarMultiplasAvaliacoes() {
        Avaliacao av1 = AvaliacaoBuilder.umaAvaliacao().construir();
        Avaliacao av2 = AvaliacaoBuilder.umaAvaliacao().construir();
        ReflectionTestUtils.setField(av1, "status", StatusAvaliacao.DISPONIVEL);
        ReflectionTestUtils.setField(av2, "status", StatusAvaliacao.DISPONIVEL);

        when(repositorio.buscarExpiradas(eq(StatusAvaliacao.DISPONIVEL), any(LocalDateTime.class)))
                .thenReturn(List.of(av1, av2));

        casoDeUso.executar();

        assertThat(av1.getStatus()).isEqualTo(StatusAvaliacao.EXPIRADA);
        assertThat(av2.getStatus()).isEqualTo(StatusAvaliacao.EXPIRADA);
        verify(repositorio).salvarLista(listaCaptor.capture());
        assertThat(listaCaptor.getValue()).hasSize(2);
    }

    @Test
    @DisplayName("Dado qualquer cenario, Quando executar, Entao deve buscar com horario proximo ao atual")
    void deveBuscarComHorarioAtual() {
        ArgumentCaptor<LocalDateTime> horarioCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        when(repositorio.buscarExpiradas(eq(StatusAvaliacao.DISPONIVEL), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        LocalDateTime antes = LocalDateTime.now();
        casoDeUso.executar();
        LocalDateTime depois = LocalDateTime.now();

        verify(repositorio).buscarExpiradas(eq(StatusAvaliacao.DISPONIVEL), horarioCaptor.capture());
        assertThat(horarioCaptor.getValue()).isBetween(antes, depois);
    }
}
