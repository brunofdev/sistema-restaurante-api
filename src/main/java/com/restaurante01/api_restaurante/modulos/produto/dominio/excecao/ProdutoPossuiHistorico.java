package com.restaurante01.api_restaurante.modulos.produto.dominio.excecao;

public class ProdutoPossuiHistorico extends RuntimeException{
    public ProdutoPossuiHistorico(String message){
        super(message);
    }
}
