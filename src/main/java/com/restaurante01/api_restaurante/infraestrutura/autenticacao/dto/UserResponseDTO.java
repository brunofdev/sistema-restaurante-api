package com.restaurante01.api_restaurante.infraestrutura.autenticacao.dto;


import com.restaurante01.api_restaurante.compartilhado.usuario_super.dominio.role.Role;

public record UserResponseDTO(String username, Role role) {
}
