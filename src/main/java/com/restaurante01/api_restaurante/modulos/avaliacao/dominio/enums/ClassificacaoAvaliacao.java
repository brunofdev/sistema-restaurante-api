package com.restaurante01.api_restaurante.modulos.avaliacao.dominio.enums;

import com.restaurante01.api_restaurante.modulos.avaliacao.dominio.objeto_de_valor.NotaAvaliacao;

public enum ClassificacaoAvaliacao {

    INSATISFEITO(1, 2),
    MODERADO(3, 3),
    SATISFEITO(4, 5);

    ClassificacaoAvaliacao(int notaMinima, int notaMaxima) {
    }

    public static ClassificacaoAvaliacao derivarDaNota(NotaAvaliacao nota) {
        return switch (nota.valor()) {
            case 1, 2 -> INSATISFEITO;
            case 3    -> MODERADO;
            default   -> SATISFEITO;
        };
    }
}