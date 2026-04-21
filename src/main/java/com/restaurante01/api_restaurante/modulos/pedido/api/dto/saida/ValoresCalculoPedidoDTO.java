package com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida;

import java.math.BigDecimal;

public record ValoresCalculoPedidoDTO(
    BigDecimal desconto,
    BigDecimal bruto,
    BigDecimal total
)
{

}
