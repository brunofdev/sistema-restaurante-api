package com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.dto;


import com.restaurante01.api_restaurante.modulos.usuario.usuario_super.role.Role;

public record UserResponseDTO(String username, Role role) {
}
