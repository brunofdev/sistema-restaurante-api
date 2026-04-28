package com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public record CupomUtilizado(
        String codigoCupom,
        BigDecimal valorBrutoTotalPedido,
        Optional<LocalDateTime> dataDoUltimoUso
) {
}
