package com.restaurante01.api_restaurante.usuarios.operador.dto.saida;

import com.restaurante01.api_restaurante.usuarios.role.Role;

public record OperadorDTO(
        Long id,
        String nome,
        String userName,
        Role role
) {
}
