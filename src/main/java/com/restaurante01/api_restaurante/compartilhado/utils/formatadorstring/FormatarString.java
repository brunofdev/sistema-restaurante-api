package com.restaurante01.api_restaurante.compartilhado.utils.formatadorstring;

public class FormatarString {
    private FormatarString() {
        // Construtor privado para evitar instanciação
    }
    public static String limparEspacos(String valor) {
        if (valor == null) return "";
        return valor.trim().replaceAll("\\s+", " ");
    }
}

