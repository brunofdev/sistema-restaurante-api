package com.restaurante01.api_restaurante.modulos.produto.dominio.excecao;

public class ProdutoIdVazioException extends RuntimeException{
    public ProdutoIdVazioException(String message){
        super(message);
    }
}
