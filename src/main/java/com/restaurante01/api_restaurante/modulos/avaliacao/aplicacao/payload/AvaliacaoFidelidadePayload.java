package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.payload;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.ClassificacaoAvaliacao;

public record AvaliacaoFidelidadePayload(
        Long clienteId,
        ClassificacaoAvaliacao classificacaoAvaliacao,
        int totalDeItensAvaliados
) {
}
