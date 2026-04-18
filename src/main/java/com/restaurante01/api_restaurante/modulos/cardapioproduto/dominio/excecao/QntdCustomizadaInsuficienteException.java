package com.restaurante01.api_restaurante.modulos.cardapioproduto.dominio.excecao;

import com.restaurante01.api_restaurante.compartilhado.dominio.excecao.RegraDeNegocioExcecao;

public class QntdCustomizadaInsuficienteException extends RegraDeNegocioExcecao {
    public QntdCustomizadaInsuficienteException(String message){
        super(message);
    }
}
