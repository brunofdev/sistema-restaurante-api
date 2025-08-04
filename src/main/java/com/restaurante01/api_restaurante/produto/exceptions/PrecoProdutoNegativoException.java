package com.restaurante01.api_restaurante.produto.exceptions;

public class PrecoProdutoNegativoException extends RuntimeException {
    public PrecoProdutoNegativoException(String mensagem) {
        super(mensagem);
    }
}