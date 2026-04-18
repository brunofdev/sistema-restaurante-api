package com.restaurante01.api_restaurante.modulos.pedido.dominio.valorobjeto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Endereco (
        @NotBlank String rua,
        @NotNull Integer numero,
        @NotBlank String bairro,
        @NotBlank String cidade,
        @NotBlank String estado,
        @NotBlank String cep,
        String referencia
) {
    @Override
    public String toString() {
        return String.format("%s, nº %s%s - %s, %s/%s, CEP: %s, Referencia: %s",
                rua,
                numero,
                (referencia != null && !referencia.isBlank() ? " (" + referencia + ")" : ""),
                bairro,
                cidade,
                cep,
                referencia);
    }
}

