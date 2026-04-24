package com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoCriacaoDTO(
        @NotNull
        @Schema(description = "Id referente ao cardapio que o produto esta vinculado", example = "1")
        Long idCardapio,
        @NotNull
        @Schema(description = "Lista de itens")
        List<ItemPedidoSolicitadoDTO> itens,
        @Schema(description = "Endereço alternativo ao endereço de cadastro")
        @Valid
        EnderecoDTO enderecoAlternativo,
        @Schema(description = "Cupom valido", example = "VALIDO1")
        String cupom
) {}
