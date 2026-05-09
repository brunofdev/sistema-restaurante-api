package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.excecao.LimiteNotificacoesAtingidoExcecao;

public enum TentativaNotificacao {
    PRIMEIRA_TENTATIVA,
    SEGUNDA_TENTATIVA,
    TERCEIRA_TENTATIVA;

    public TentativaNotificacao proxima() {
        return switch (this) {
            case PRIMEIRA_TENTATIVA -> SEGUNDA_TENTATIVA;
            case SEGUNDA_TENTATIVA -> TERCEIRA_TENTATIVA;
            case TERCEIRA_TENTATIVA -> throw new LimiteNotificacoesAtingidoExcecao(
                    "Limite máximo de notificações atingido."
            );
        };
    }
    public boolean isUltimaTentativa() {
        return this == TERCEIRA_TENTATIVA;
    }
}
