package com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;

public enum TipoDesconto {
    PORCENTAGEM(
            (valorTotalBruto, desconto) -> valorTotalBruto
                    .multiply(desconto)
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP
            )),
    VALOR((valorTotalBruto, desconto) -> desconto);


    private final BiFunction<BigDecimal, BigDecimal, BigDecimal> operacao;

    TipoDesconto(BiFunction<BigDecimal, BigDecimal, BigDecimal> operacao){
        this.operacao = operacao;
    }

    public BigDecimal aplicar(BigDecimal valorTotalBruto, BigDecimal desconto){
        return operacao.apply(valorTotalBruto, desconto);
    }
}
