package com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.CupomNaoPodeSerConsumidoExcecao;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;




class RegraRecorrenciaTest {

    @Test
    void deveLancarExcecaoQuandoCupomUsadoDentroDoIntervalo() {
        LocalDateTime ontem = LocalDateTime.now().minusDays(1);

        assertThatThrownBy(() -> RegraRecorrencia.DEZ_DIAS.aplicar(ontem))
                .isInstanceOf(CupomNaoPodeSerConsumidoExcecao.class);
    }

    @Test
    void naoDeveLancarExcecaoQuandoCupomUsadoForaDoIntervalo() {
        LocalDateTime haOnzeDias = LocalDateTime.now().minusDays(11);

        assertThatCode(() -> RegraRecorrencia.DEZ_DIAS.aplicar(haOnzeDias))
                .doesNotThrowAnyException();
    }
}