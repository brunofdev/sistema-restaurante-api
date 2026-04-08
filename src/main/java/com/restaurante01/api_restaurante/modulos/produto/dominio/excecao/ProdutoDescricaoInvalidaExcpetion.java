package com.restaurante01.api_restaurante.modulos.produto.dominio.excecao;

public class ProdutoDescricaoInvalidaExcpetion extends RuntimeException{
    public ProdutoDescricaoInvalidaExcpetion(String message){
        super(message);
    }
}
