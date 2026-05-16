package com.restaurante01.api_restaurante.modulos.avaliacao.aplicacao.evento;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums.ClassificacaoAvaliacao;

public record AvaliacaoConcluidaEvento (
        Long avaliacaoId,
        Long clienteId,
        ClassificacaoAvaliacao classificacaoAvaliacao,
        int totalDeItensAvaliados
){
}
