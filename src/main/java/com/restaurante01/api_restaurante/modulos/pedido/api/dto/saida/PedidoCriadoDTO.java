package com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.EnderecoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PedidoCriadoDTO(
        Long id,
        String nomeUsuario,
        String cpfUsuario,
        String whatsappUsuario,
        List<ItemPedidoDTO> itens,
        ValoresCalculoPedidoDTO valores,
        StatusPedido statusPedido,
        EnderecoDTO enderecoDeEntrega,
        String cupomAplicado
) {
}
