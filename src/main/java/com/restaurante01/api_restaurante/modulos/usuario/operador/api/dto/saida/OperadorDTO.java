package com.restaurante01.api_restaurante.modulos.usuario.operador.api.dto.saida;

import com.restaurante01.api_restaurante.modulos.usuario.dominio.role.Role;

public record OperadorDTO(
        Long id,
        String nome,
        String userName,
        Role role
) {
}
