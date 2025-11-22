package com.restaurante01.api_restaurante.autenticacao.dto;


import com.restaurante01.api_restaurante.usuarios.enums.UserRole;

public record UserResponseDTO(String username, UserRole role) {
}
