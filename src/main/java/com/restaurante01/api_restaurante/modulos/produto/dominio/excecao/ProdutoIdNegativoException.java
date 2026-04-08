package com.restaurante01.api_restaurante.modulos.produto.dominio.excecao;

public class ProdutoIdNegativoException extends RuntimeException{
    public ProdutoIdNegativoException(String message){
        super(message);
    }
}
