package com.restaurante01.api_restaurante.modulos.avaliacao.dominio;

import jakarta.persistence.Embeddable;

@Embeddable
public record ComentarioAvaliacao(
        String comentario
) {
}
