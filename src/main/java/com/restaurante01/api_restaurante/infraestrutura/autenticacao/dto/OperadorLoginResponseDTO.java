package com.restaurante01.api_restaurante.infraestrutura.autenticacao.dto;


import com.restaurante01.api_restaurante.modulos.operador.dto.saida.OperadorDTO;


public record OperadorLoginResponseDTO(String token, OperadorDTO operadorDTO) {
}
