package com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.servico;



import java.math.BigDecimal;

public class CalculadoraDeFidelidade {
    private static final BigDecimal LIMITE_FAIXA_BRONZE = BigDecimal.valueOf(50);
    private static final BigDecimal LIMITE_FAIXA_PRATA = BigDecimal.valueOf(90);

    private static final int PONTOS_BRONZE = 1;
    private static final int PONTOS_PRATA = 2;
    private static final int PONTOS_OURO = 3;

    public static int calcular(BigDecimal totalPedido) {
        if (totalPedido == null) return 0;
        if (totalPedido.compareTo(LIMITE_FAIXA_BRONZE) <= 0) return PONTOS_BRONZE;
        if (totalPedido.compareTo(LIMITE_FAIXA_PRATA) <= 0) return PONTOS_PRATA;
        return PONTOS_OURO;
    }
}