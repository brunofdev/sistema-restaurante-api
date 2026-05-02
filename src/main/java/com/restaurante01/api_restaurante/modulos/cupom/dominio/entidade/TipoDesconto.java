package com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;

public enum TipoDesconto {
    PORCENTAGEM {
        @Override
        public BigDecimal aplicar(BigDecimal valorTotalBruto, BigDecimal desconto) {
            return valorTotalBruto
                    .multiply(desconto)
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        }
    },
    VALOR {
        @Override
        public BigDecimal aplicar(BigDecimal valorTotalBruto, BigDecimal desconto) {
            return desconto;
        }
    };

    public abstract BigDecimal aplicar(BigDecimal valorTotalBruto, BigDecimal desconto);
}
