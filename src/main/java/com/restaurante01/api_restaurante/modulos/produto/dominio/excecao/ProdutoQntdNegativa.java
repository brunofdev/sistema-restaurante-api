package com.restaurante01.api_restaurante.modulos.produto.dominio.excecao;

public class ProdutoQntdNegativa extends RuntimeException{
    public ProdutoQntdNegativa(String message){
        super(message);
    }
}
