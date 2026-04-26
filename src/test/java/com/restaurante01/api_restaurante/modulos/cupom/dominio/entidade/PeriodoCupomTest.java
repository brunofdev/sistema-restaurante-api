package com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.PeriodoInvalidoExcecao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class PeriodoCupomTest {

    private final String hoje = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    private final String amanha = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    private final String ontem = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    @Test
    @DisplayName("Deve criar período com sucesso quando datas forem válidas")
    void deveCriarPeriodoValido() {
        PeriodoCupom periodo = new PeriodoCupom("10/10/2026", "10:00", "20/10/2026", "23:59");
        assertNotNull(periodo);
        assertEquals("10:00", periodo.getHoraInicio().toString());
    }

    @Test
    @DisplayName("Deve lançar exceção se data inicial for posterior à data final")
    void deveLancarExcecaoQuandoInicioAposFim() {
        assertThrows(PeriodoInvalidoExcecao.class, () ->
                new PeriodoCupom("20/10/2026", "10:00", "10/10/2026", "23:59")
        );
    }

    @Test
    @DisplayName("Deve identificar corretamente quando o cupom está vigente no momento atual")
    void deveRetornarVerdadeiroQuandoEstaVigente() {
        PeriodoCupom periodoVigente = new PeriodoCupom(ontem, "00:00", amanha, "23:59");
        assertTrue(periodoVigente.estaVigente());
    }

    @Test
    @DisplayName("Deve identificar corretamente quando o cupom NÃO está vigente (já expirou)")
    void deveRetornarFalsoQuandoNaoEstaVigente() {
        PeriodoCupom periodoExpirado = new PeriodoCupom("01/01/2020", "00:00", "02/01/2020", "23:59");
        assertFalse(periodoExpirado.estaVigente());
    }
}