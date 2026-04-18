package com.restaurante01.api_restaurante.modulos.pedido.dominio.entidade;

public record InformacoesClienteParaPedido(
        Long clienteId,
        String nome,
        String cpf,
        String telefone
) {
}
