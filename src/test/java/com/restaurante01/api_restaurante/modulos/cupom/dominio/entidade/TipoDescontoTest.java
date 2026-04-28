package com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TipoDescontoTest {

    @Test
    void deveCalcularDescontoPorPorcentagemCorretamente() {
        BigDecimal valorBruto = new BigDecimal("100.00");
        BigDecimal porcentagem = new BigDecimal("10");

        BigDecimal resultado = TipoDesconto.PORCENTAGEM.aplicar(valorBruto, porcentagem);

        assertThat(resultado).isEqualByComparingTo(new BigDecimal("10.00"));
    }
}