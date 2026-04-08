package com.restaurante01.api_restaurante.modulos.produto.dominio.excecao;

public class ProdutoNaoEncontradoException extends RuntimeException{
    public ProdutoNaoEncontradoException (String message){
        super(message);
    }
}
