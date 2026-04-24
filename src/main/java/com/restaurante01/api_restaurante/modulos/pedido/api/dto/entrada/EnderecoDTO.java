package com.restaurante01.api_restaurante.modulos.pedido.api.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoDTO(
        @Schema(description = "Rua alternativa", example = "Tres pinheiros um")
        @NotBlank String rua,
        @Schema(description = "Numero da residencia", example = "27")
        @NotNull  Integer numero,
        @Schema(description = "Bairro da cidade", example = "Mato grande")
        @NotBlank String bairro,
        @Schema(description = "Cidade do Estado", example = "Canoas")
        @NotBlank String cidade,
        @Schema(description = "UF referente", example = "RS")
        @NotBlank String estado,
        @Schema(description = "Cep do local", example = "88058209")
        @NotBlank String cep,
        @Schema(description = "Ponto de referencia", example = "Casa branco de badeira portao de ferro")
        String referencia
) {
    @Override
    @NotNull
    public String toString() {
        return String.format("%s, nº %d, %s - %s/%s, CEP: %s%s",
                rua,
                numero,
                bairro,
                cidade,
                estado,
                cep,
                (referencia == null || referencia.isBlank() ? "Sem referencia" : " (" + referencia + ")"));
    }
}