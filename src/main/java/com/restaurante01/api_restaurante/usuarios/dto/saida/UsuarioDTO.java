package com.restaurante01.api_restaurante.usuarios.dto.saida;

import com.restaurante01.api_restaurante.usuarios.enums.Role;

public record UsuarioDTO(
        Long id,
        String nome,
        String userName,
        Role role
) {
}
