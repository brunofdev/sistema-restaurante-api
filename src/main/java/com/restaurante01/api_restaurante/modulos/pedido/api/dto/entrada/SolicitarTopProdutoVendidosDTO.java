package com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;


public record SolicitarTopProdutoVendidosDTO(
       @NotNull
       @Schema(description = "Data de inicio do intervalo a ser procurado")
       LocalDate dataIni,
       @NotNull
       @Schema(description = "Id referente ao cardápio que o produto esta vinculado")
       LocalDate dataFim,
       @NotNull
       @Schema(description = "Numero de produtos para serem retornados, por exemplo top 5 vendidos durante aquele período", example = "5")
       Integer quantidadeParaRetornar
) {
}
