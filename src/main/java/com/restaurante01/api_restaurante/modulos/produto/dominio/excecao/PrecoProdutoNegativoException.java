package com.restaurante01.api_restaurante.modulos.produto.dominio.excecao;

public class PrecoProdutoNegativoException extends RuntimeException {
    public PrecoProdutoNegativoException(String mensagem) {
        super(mensagem);
    }
}