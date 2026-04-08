package com.restaurante01.api_restaurante.infraestrutura.autenticacao.dto;

import com.restaurante01.api_restaurante.modulos.cliente.api.dto.saida.ClienteDTO;


public record ClienteLoginResponseDTO(String token, ClienteDTO clienteDTO) {
}
