package com.restaurante01.api_restaurante.produto.exceptions;

import com.restaurante01.api_restaurante.produto.repository.ProdutoRepository;

public class ProdutoMesmoNomeExistenteException extends RuntimeException{
    public ProdutoMesmoNomeExistenteException (String message) {
        super(message);
    }
}
