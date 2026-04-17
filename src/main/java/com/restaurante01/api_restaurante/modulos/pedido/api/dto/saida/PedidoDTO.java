package com.restaurante01.api_restaurante.modulos.pedido.api.dto.saida;

import com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada.EnderecoDTO;
import com.restaurante01.api_restaurante.modulos.pedido.dominio.enums.StatusPedido;

import java.math.BigDecimal;
import java.util.List;

public record PedidoDTO(
        Long id,
        String nomeUsuario,
        String cpfUsuario,
        String whatsappUsuario,
        List<ItemPedidoDTO> itens,
        BigDecimal valorTotal,
        StatusPedido statusPedido,
        EnderecoDTO enderecoDeEntrega
) {
}
