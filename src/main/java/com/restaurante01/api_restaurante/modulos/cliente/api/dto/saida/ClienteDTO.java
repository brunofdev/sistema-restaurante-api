package com.restaurante01.api_restaurante.modulos.cliente.api.dto.saida;

import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.role.Role;

public record ClienteDTO(
        Long id,
        String nome,
        String userName,
        Role role,
        int pontuacaoFidelidade
) {
}
