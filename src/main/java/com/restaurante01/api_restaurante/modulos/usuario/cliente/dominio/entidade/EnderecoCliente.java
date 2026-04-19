package com.restaurante01.api_restaurante.modulos.usuario.cliente.dominio.entidade;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Embeddable
public record EnderecoCliente(
        @NotBlank String rua,
        @NotNull Integer numero,
        @NotBlank String bairro,
        @NotBlank String cidade,
        @NotBlank String estado,
        @NotBlank String cep,
        @NotBlank String complemento,
        String referencia
) {
    @Override
    public String toString() {
        return String.format("%s, nº %s%s - %s, %s/%s, CEP: %s, Referencia: %s",
                rua,
                numero,
                bairro,
                cidade,
                estado,
                cep,
                complemento,
                (referencia != null && !referencia.isBlank() ? " (" + referencia + ")" : ""));
    }
}


