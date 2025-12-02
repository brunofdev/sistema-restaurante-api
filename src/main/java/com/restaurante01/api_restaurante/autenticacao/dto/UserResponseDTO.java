package com.restaurante01.api_restaurante.autenticacao.dto;


import com.restaurante01.api_restaurante.usuarios.enums.Role;

public record UserResponseDTO(String username, Role role) {
}
