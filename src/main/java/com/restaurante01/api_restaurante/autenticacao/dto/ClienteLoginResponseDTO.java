package com.restaurante01.api_restaurante.autenticacao.dto;

import com.restaurante01.api_restaurante.usuarios.cliente.dto.saida.ClienteDTO;


public record ClienteLoginResponseDTO(String token, ClienteDTO clienteDTO) {
}
