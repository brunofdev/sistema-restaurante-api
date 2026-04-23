package com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoDTO(
        @NotBlank String rua,
        @NotNull  Integer numero,
        @NotBlank String bairro,
        @NotBlank String cidade,
        @NotBlank String estado,
        @NotBlank String cep,
        String referencia
) {
    @Override
    public String toString() {
        return String.format("%s, nº %d, %s - %s/%s, CEP: %s%s",
                rua,
                numero,
                bairro,
                cidade,
                estado,
                cep,
                (referencia == null || referencia.isBlank() ? "" : " (" + referencia + ")"));
    }
}