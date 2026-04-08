package com.restaurante01.api_restaurante.modulos.operador.dto.saida;

import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.role.Role;

public record OperadorDTO(
        Long id,
        String nome,
        String userName,
        Role role
) {
}
