package com.restaurante01.api_restaurante.modulos.produto.dominio.excecao;

public class ProdutoNomeVazioException extends RuntimeException{
    public ProdutoNomeVazioException(String message){
        super(message);
    }
}
