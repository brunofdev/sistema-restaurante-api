package com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.dto;


import com.restaurante01.api_restaurante.modulos.usuario.operador.api.dto.saida.OperadorDTO;


public record OperadorLoginResponseDTO(String token, OperadorDTO operadorDTO) {
}
