package com.restaurante01.api_restaurante.modulos.fidelidade.dominio.objeto_de_valor;

import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.excecao.MotivoRegistroVazioExcecao;
import com.restaurante01.api_restaurante.modulos.fidelidade.dominio.excecao.PontuacaoAtualInvalidaExcecao;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public record RegistroPontuacao(
        Integer ultimaPontuacaoGerada,
        String motivo,
        LocalDateTime dataComputacao,
        Integer saldoNoDia
) {
    public RegistroPontuacao {
        if (ultimaPontuacaoGerada == null || ultimaPontuacaoGerada <= 0) {
            throw new PontuacaoAtualInvalidaExcecao("A pontuação gerada no registro deve ser positiva.");
        }
        if (motivo == null || motivo.isBlank()) {
            throw new MotivoRegistroVazioExcecao("O motivo do registro de pontuação não pode ser vazio.");
        }
        if (dataComputacao == null) {
            throw new PontuacaoAtualInvalidaExcecao("A data de computação do registro não pode ser nula.");
        }
        if (saldoNoDia == null || saldoNoDia < 0) {
            throw new PontuacaoAtualInvalidaExcecao("O saldo no dia não pode ser nulo ou negativo.");
        }
    }

    public static RegistroPontuacao criar(Integer pontos, String motivo, Integer saldoNoDia) {
        return new RegistroPontuacao(pontos, motivo, LocalDateTime.now(), saldoNoDia);
    }
}
