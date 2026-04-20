package com.restaurante01.api_restaurante.modulos.usuario.cliente.api.dto.saida;

import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.role.Role;

public record ClienteDTO(
        Long id,
        String nome,
        String cpf,
        Role role,
        int pontuacaoFidelidade
) {
}
