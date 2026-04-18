package com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto;

public record InformacoesClienteParaPedido(
        Long clienteId,
        String nome,
        String cpf,
        String telefone
) {
}
