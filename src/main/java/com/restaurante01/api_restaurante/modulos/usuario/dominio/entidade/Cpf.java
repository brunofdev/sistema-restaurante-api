package com.restaurante01.api_restaurante.modulos.usuario.dominio.entidade;

import com.restaurante01.api_restaurante.modulos.usuario.dominio.exceptions.CpfInvalidoExcecao;
import jakarta.persistence.Embeddable;

@Embeddable
public record Cpf(String cpf) {

    public Cpf {
        if (cpf == null || cpf.isBlank())
            throw new CpfInvalidoExcecao("CPF não pode ser vazio");
        cpf = cpf.replaceAll("[^\\d]", "");
        if (cpf.length() != 11)
            throw new CpfInvalidoExcecao("CPF deve ter 11 dígitos");
        /*
        if (!isValid(value))
            throw new IllegalArgumentException("CPF inválido: " + value);
        */
    }

    //Quando quiser validar cpf, basta descomentar isso
   /*
   private static boolean isValid(String cpf) {
        if (cpf.chars().distinct().count() == 1) return false;

        int sum = 0;
        for (int i = 0; i < 9; i++) sum += (cpf.charAt(i) - '0') * (10 - i);
        int first = 11 - (sum % 11);
        if (first >= 10) first = 0;

        sum = 0;
        for (int i = 0; i < 10; i++) sum += (cpf.charAt(i) - '0') * (11 - i);
        int second = 11 - (sum % 11);
        if (second >= 10) second = 0;

        return first == (cpf.charAt(9) - '0') && second == (cpf.charAt(10) - '0');
    }
    */

    public String formatted() {
        return "%s.%s.%s-%s".formatted(
                cpf.substring(0, 3), cpf.substring(3, 6),
                cpf.substring(6, 9), cpf.substring(9, 11)
        );
    }
}