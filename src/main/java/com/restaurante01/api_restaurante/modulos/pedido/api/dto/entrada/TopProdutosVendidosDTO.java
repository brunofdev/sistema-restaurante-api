package com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TopProdutosVendidosDTO(
       @NotNull LocalDateTime dataIni,
       @NotNull LocalDateTime dataFim,
       @NotNull Integer quantidadeParaRetornar
) {
}
