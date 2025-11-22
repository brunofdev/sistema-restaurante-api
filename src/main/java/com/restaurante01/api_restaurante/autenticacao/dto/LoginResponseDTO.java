package com.restaurante01.api_restaurante.autenticacao.dto;

import com.restaurante01.api_restaurante.usuarios.dto.saida.UsuarioDTO;

public record LoginResponseDTO(String token, UsuarioDTO usuarioDTO) {
}
