package com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto;


import jakarta.persistence.Embeddable;

@Embeddable
public record InformacoesClienteParaPedido(
        Long clienteId,
        String nome,
        String cpf,
        String telefone
) {
}
