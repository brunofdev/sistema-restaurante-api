package com.restaurante01.api_restaurante.modulos.produto.dominio.excecao;

public class ProdutoMesmoNomeExistenteException extends RuntimeException{
    public ProdutoMesmoNomeExistenteException (String message) {
        super(message);
    }
}
