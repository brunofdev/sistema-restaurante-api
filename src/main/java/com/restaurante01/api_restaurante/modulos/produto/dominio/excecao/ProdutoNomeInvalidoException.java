package com.restaurante01.api_restaurante.modulos.produto.dominio.excecao;

public class ProdutoNomeInvalidoException extends  RuntimeException{
    public ProdutoNomeInvalidoException(String message){
        super(message);
    }

}
