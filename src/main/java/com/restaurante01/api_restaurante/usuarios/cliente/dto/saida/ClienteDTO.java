package com.restaurante01.api_restaurante.usuarios.cliente.dto.saida;

import com.restaurante01.api_restaurante.usuarios.role.Role;

public record ClienteDTO(
        Long id,
        String nome,
        String userName,
        Role role,
        int pontuacaoFidelidade
) {
}
