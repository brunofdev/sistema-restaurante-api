package com.restaurante01.api_restaurante.modulos.produto.dominio.excecao;

public class ProdutoPrecoVazioException extends RuntimeException{
    public ProdutoPrecoVazioException(String message){
        super(message);
    }
}
