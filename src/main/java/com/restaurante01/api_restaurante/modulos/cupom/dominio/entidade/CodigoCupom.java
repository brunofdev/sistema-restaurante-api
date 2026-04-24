package com.restaurante01.api_restaurante.modulos.cupom.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.cupom.dominio.excecao.CodigoCupomInvalidoExcecao;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class CodigoCupom {

    private static final int TAMANHO_MINIMO = 5;
    private static final int TAMANHO_MAXIMO = 15;
    private static final String FORMATO_VALIDO = "^[A-Z0-9]+$";

    private String valor;

    protected CodigoCupom() {}

    public CodigoCupom(String valor) {
        validar(valor);
        this.valor = valor;
    }

    private void validar(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new CodigoCupomInvalidoExcecao("Código do cupom não pode ser nulo ou vazio");
        }
        if (valor.length() < TAMANHO_MINIMO) {
            throw new CodigoCupomInvalidoExcecao(
                    "Código do cupom deve ter no mínimo " + TAMANHO_MINIMO + " caracteres");
        }
        if (valor.length() > TAMANHO_MAXIMO) {
            throw new CodigoCupomInvalidoExcecao(
                    "Código do cupom deve ter no máximo " + TAMANHO_MAXIMO + " caracteres");
        }
        if (!valor.matches(FORMATO_VALIDO)) {
            throw new CodigoCupomInvalidoExcecao(
                    "Código do cupom deve conter apenas letras maiúsculas e números, sem espaços ou caracteres especiais");
        }
    }

    @Override
    public String toString() { return valor; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodigoCupom)) return false;
        CodigoCupom that = (CodigoCupom) o;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() { return valor.hashCode(); }
}