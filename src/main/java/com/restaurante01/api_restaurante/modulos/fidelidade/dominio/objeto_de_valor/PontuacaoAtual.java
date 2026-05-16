package com.restaurante01.api_restaurante.modulos.fidelidade.dominio.objeto_de_valor;

import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.excecao.PontuacaoAtualInvalidaExcecao;
import jakarta.persistence.Embeddable;

@Embeddable
public record PontuacaoAtual(Integer valor) {

    public PontuacaoAtual {
        if (valor == null || valor < 0) {
            throw new PontuacaoAtualInvalidaExcecao("A pontuação atual não pode ser nula ou negativa.");
        }
    }

    public static PontuacaoAtual zero() {
        return new PontuacaoAtual(0);
    }

    public PontuacaoAtual acrescentar(Integer pontos) {
        if (pontos == null || pontos <= 0) {
            throw new PontuacaoAtualInvalidaExcecao("Os pontos a acrescentar devem ser positivos.");
        }
        return new PontuacaoAtual(this.valor + pontos);
    }
}
