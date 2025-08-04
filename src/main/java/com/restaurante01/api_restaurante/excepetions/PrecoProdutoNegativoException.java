package com.restaurante01.api_restaurante.excepetions;

public class PrecoProdutoNegativoException extends RuntimeException {
    public PrecoProdutoNegativoException(String mensagem) {
        super(mensagem);
    }
}
