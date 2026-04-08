package com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.excecao;

public class AssociacaoExistenteCardapioProdutoException extends RuntimeException{
    public AssociacaoExistenteCardapioProdutoException(String message){
        super(message);
    }
}
