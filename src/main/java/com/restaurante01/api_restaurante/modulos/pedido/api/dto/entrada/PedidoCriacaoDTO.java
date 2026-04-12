package com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoCriacaoDTO(
        @NotNull
        Long idCardapio,
        @NotNull
        List<ItemPedidoSolicitadoDTO> itens
) {}
