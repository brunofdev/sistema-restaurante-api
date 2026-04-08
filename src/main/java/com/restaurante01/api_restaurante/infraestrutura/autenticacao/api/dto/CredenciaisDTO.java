package com.restaurante01.api_restaurante.infraestrutura.autenticacao.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CredenciaisDTO(

        @NotBlank(message = "O cpf não pode estar em branco.")
        @Schema(description = "CPF do usuário apenas com números", example = "00000000000")
        String cpf,

        @NotBlank(message = "Senha não pode estar em branco.")
        @Schema(description = "Senha secreta de acesso", example = "123456")
        String senha) {
}