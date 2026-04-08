package com.restaurante01.api_restaurante.modulos.produto.dominio.excecao;

public class ProdutoDisponibilidadeVazioException extends RuntimeException{
    public ProdutoDisponibilidadeVazioException(String message){
        super(message);
    }
}
