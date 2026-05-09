package com.restaurante01.api_restaurante.modulos.avaliacao.dominio;

import jakarta.persistence.Embeddable;

@Embeddable
public record NotaAvaliacao(
        Integer valor
) {
}
