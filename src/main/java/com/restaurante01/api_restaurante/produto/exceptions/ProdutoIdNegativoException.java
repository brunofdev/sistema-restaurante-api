package com.restaurante01.api_restaurante.produto.exceptions;

import com.restaurante01.api_restaurante.produto.entity.Produto;

public class ProdutoIdNegativoException extends RuntimeException{
    public ProdutoIdNegativoException(String message){
        super(message);
    }
}
