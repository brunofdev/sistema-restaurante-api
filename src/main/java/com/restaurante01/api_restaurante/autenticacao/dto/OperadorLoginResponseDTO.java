package com.restaurante01.api_restaurante.autenticacao.dto;


import com.restaurante01.api_restaurante.usuarios.operador.dto.saida.OperadorDTO;


public record OperadorLoginResponseDTO(String token, OperadorDTO operadorDTO) {
}
