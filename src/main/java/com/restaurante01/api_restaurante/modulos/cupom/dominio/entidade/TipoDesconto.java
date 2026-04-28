package com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum TipoDesconto {
    PORCENTAGEM{
        @Override
        public BigDecimal aplicar(BigDecimal valorTotalBruto, BigDecimal valorDesconto){
            return valorTotalBruto
                    .multiply(valorDesconto)
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        }
    },
    VALOR {
        @Override
        public BigDecimal aplicar(BigDecimal valorTotalBruto, BigDecimal valorDesconto) {
            return valorDesconto;
        }
    };
    public abstract BigDecimal aplicar(BigDecimal valorTotalBruto, BigDecimal valorDesconto);

}
