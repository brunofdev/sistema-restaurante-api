package com.restaurante01.api_restaurante.core.utils.validadorcpf;

import java.util.ArrayList;

public class ValidadorCpf {
    private ValidadorCpf() {
    }

    private static String limparCpf(String cpf) {
        cpf = cpf.replaceAll("[^\\d]", "");
        if (cpf.length() > 11) {
            throw new FormatarCpfException("O cpf informado possui MAIS de 11 digitos");
        } else if (cpf.length() < 11) {
            throw new FormatarCpfException("O cpf informado possui MENOS de 11 digitos");
        }
        return cpf;
    }
    public static void validarCpf(String cpf) {
        cpf = limparCpf(cpf);
        if (cpf.matches("(\\d)\\1{10}")) {
            throw new CpfComTamanhoInvalidoException("Verifique o tamanho do cpf enviado");
        }
        for (int j = 9; j < 11; j++) {
            int soma = 0;
            for (int i = 0; i < j; i++) {
                soma += (cpf.charAt(i) - '0') * (j + 1 - i);
            }
            int digito = (soma * 10) % 11;
            if (digito == 10) {
                digito = 0;
            }
            if (digito != (cpf.charAt(j) - '0')) {
                throw new NumeroCpfInvalidoException("O numero do cpf informado é inválido");
            }
        }
    }
    public static String formatarCpf(String cpf){
        cpf = limparCpf(cpf);
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
    public static ArrayList<String> formatarVariosCpf(ArrayList<String>listaDeCpf){
        for(String cpf : listaDeCpf){
            formatarCpf(cpf);
        }
        return listaDeCpf;
    }
}
