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
        return String.format("%s, %s, nº %s%s - %s, %s/%s, CEP: %s, Referencia: %s",
                estado,
                rua,
                numero,
                bairro,
                cidade,
                cep,
                (referencia == null || referencia.isBlank() ? "Cliente não adicionou referencia" : "(" + referencia + ")"));
    }
}