package com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida;

import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.EnderecoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;


import java.time.LocalDateTime;
import java.util.List;

public record PedidoDetalhadoDTO(
        Long id,
        String nomeUsuario,
        String cpfUsuario,
        String whatsappUsuario,
        List<ItemPedidoDTO> itens,
        ValoresCalculoPedidoDTO valores,
        StatusPedido statusPedido,
        EnderecoDTO enderecoDeEntrega,
        InformacoesCupomDTO cupomDTO,
        LocalDateTime dataCriacao
) {
}
